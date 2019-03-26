package mz.co.basse.finance.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.Sale;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.finance.core.dao.PaymentDao;
import mz.co.basse.finance.core.model.Payment;

@Repository(value = "paymentDao")
public class PaymentJpaDao implements PaymentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void createPayment(Payment payment) {
		entityManager.persist(payment);
	}

	@Override
	public void updatePayment(Payment payment) {
		entityManager.merge(payment);
	}

	@Override
	public List<Payment> findPayments(Client client, User user, String receiptNumber, 
			Date startDate, Date endDate, Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Payment> criteriaQuery = criteriaBuilder
				.createQuery(Payment.class);
		Root<Payment> payment = criteriaQuery.from(Payment.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(payment.get("active"),
					active);
			predicates.add(predicate);
		}
		if (startDate != null) {
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(
					payment.<Date> get("date"), startDate);
			predicates.add(predicate);
		}
		if (endDate != null) {
			Predicate predicate = criteriaBuilder
					.lessThanOrEqualTo(payment.<Date> get("date"), endDate);
			predicates.add(predicate);
		}
		if (receiptNumber != null) {

			Predicate predicate = criteriaBuilder.like(
					payment.<String> get("receiptNumber"),
					"%" + receiptNumber + "%");
			predicates.add(predicate);
		}
		if (client != null) {
			Predicate predicate = criteriaBuilder.equal(payment.get("client"),
					client);
			predicates.add(predicate);
		}
		if (user != null) {
			Predicate predicate = criteriaBuilder.equal(payment.get("user"),
					client);
			predicates.add(predicate);
		}
		criteriaQuery
				.where(predicates.toArray(new Predicate[predicates.size()]));
		criteriaQuery.select(payment)
				.orderBy(criteriaBuilder.desc(payment.get("date")));
		TypedQuery<Payment> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public Payment findPayment(Sale sale) {
		TypedQuery<Payment> query = entityManager.createQuery(
				"select payment from Payment payment where payment.sale = :sale",
				Payment.class);
		query.setParameter("sale", sale);
		List<Payment> payments = query.getResultList();
		if (payments.isEmpty()) {
			return null;
		}
		return payments.get(0);
	}
}
