package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mz.co.basse.inbasse.accesscontrol.core.dao.SupplierDao;
import mz.co.basse.inbasse.accesscontrol.core.model.Supplier;

@Repository(value = "supplierDao")
public class SupplierJpaDao implements SupplierDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Supplier supplier) {
		entityManager.persist(supplier);
	}

	@Override
	public List<Supplier> find(Long id, String name, String email, String address, String phone, boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Supplier> criteriaQuery = criteriaBuilder.createQuery(Supplier.class);
		Root<Supplier> root = criteriaQuery.from(Supplier.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (id != null) {
			Predicate predicate = criteriaBuilder.equal(root.<Long>get("id"), id);
			predicates.add(predicate);
		} else {
			if (name != null && !name.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(root.<String>get("name"), "%" + name + "%");
				predicates.add(predicate);
			}
			if (phone != null && !phone.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(root.<String>get("phone"), "%" + phone + "%");
				predicates.add(predicate);
			}

			if (email != null && !email.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(root.<String>get("email"), "%" + email + "%");
				predicates.add(predicate);
			}
			Predicate predicate = criteriaBuilder.equal(root.get("active"), active);
			predicates.add(predicate);
		}
		criteriaQuery.select(root).where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(criteriaBuilder.desc(root.get("name")));
		TypedQuery<Supplier> query = entityManager.createQuery(criteriaQuery);
		List<Supplier> resultList = query.getResultList();
		if (resultList.isEmpty())
			return null;
		else
			return resultList;
	}

	@Override
	public void update(Supplier supplier) {
		entityManager.merge(supplier);
		
	}

}
