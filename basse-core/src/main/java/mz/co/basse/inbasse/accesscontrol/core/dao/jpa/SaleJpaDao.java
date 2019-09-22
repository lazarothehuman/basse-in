package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mz.co.basse.inbasse.accesscontrol.core.dao.SaleDao;
import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.PaymentStatus;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.finance.core.model.Sale;

@Repository(value = "saleDao")
public class SaleJpaDao implements SaleDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public void create(Sale sale) {
		entityManager.persist(sale);
	}

	public Sale findRequest(String name) {
		TypedQuery<Sale> query = entityManager
				.createQuery(
						"select distinct sale from Sale sale "
								+ "where name = :name",
						Sale.class);
		query.setParameter("name", name);
		List<Sale> sales = query.getResultList();
		if (sales.isEmpty()) {
			return null;
		}
		return sales.get(0);
	}

	public List<Sale> findRequests(Boolean active,
			Client client, User user, Date startDate, Date endDate,
			List<PaymentStatus> paymentStatus, Integer resultsLimit) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sale> criteriaQuery = criteriaBuilder
				.createQuery(Sale.class);
		Root<Sale> sale = criteriaQuery
				.from(Sale.class);

		// sale.fetch("payment", JoinType.LEFT);
		Join<Object, Object> payments = sale.join("payment",
				JoinType.LEFT);

		// sale.fetch("cargoItems", JoinType.LEFT);
		Join<Object, Object> productSaleItems = sale.join("productSaleItems",
				JoinType.LEFT);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (active != null) {
			Predicate predicate = criteriaBuilder
					.equal(sale.get("active"), active);
			predicates.add(predicate);
		}
		if (client != null) {
			Predicate predicate = criteriaBuilder
					.equal(sale.get("client"), client);
			predicates.add(predicate);
		}
		if (user != null) {
			Predicate predicate = criteriaBuilder
					.equal(sale.get("registrator"), user);
			predicates.add(predicate);
		}
		if (startDate != null) {
			Predicate greaterThanOrEqualPredicate = criteriaBuilder
					.greaterThanOrEqualTo(
							sale.<Date> get("date"), startDate);
			predicates.add(greaterThanOrEqualPredicate);
		}
		if (endDate != null) {
			Predicate lessThanPredicate = criteriaBuilder
					.lessThan(sale.<Date> get("date"), endDate);
			predicates.add(lessThanPredicate);
		}
		if (paymentStatus != null && !paymentStatus.isEmpty()) {
			Predicate predicate = sale.get("paymentStatus")
					.in(paymentStatus);
			predicates.add(predicate);
		}

		criteriaQuery
				.where(predicates.toArray(new Predicate[predicates.size()]));
		criteriaQuery.select(sale).distinct(true)
				.orderBy(criteriaBuilder.desc(sale.get("id")));
		TypedQuery<Sale> query = entityManager
				.createQuery(criteriaQuery);
		if (resultsLimit != null) {
			query.setMaxResults(resultsLimit);
		}
		return query.getResultList();
	}

	public void update(Sale sale) {
		entityManager.merge(sale);
	}

	@Override
	public Sale findLastSales(int year) {
		TypedQuery<Sale> query = entityManager.createQuery(
				"select sale from Sale request where sale.year = :year order by sale.invoiceNumberSequence desc ",
				Sale.class);
		query.setParameter("year", year);
		query.setMaxResults(1);
		List<Sale> requests = query.getResultList();
		if (requests.isEmpty()) {
			return null;
		}
		return requests.get(0);
	}

}
