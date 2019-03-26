package mz.co.basse.finance.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mz.co.basse.finance.core.dao.BankDao;
import mz.co.basse.finance.core.model.Bank;

@Repository(value = "bankDao")
public class BankJpaDao implements BankDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void createBank(Bank bank) {
		entityManager.persist(bank);
	}

	@Override
	public void update(Bank bank) {
		entityManager.merge(bank);
	}

	@Override
	public Bank findBank(String name) {
		TypedQuery<Bank> query = entityManager.createQuery(
				"select b from Bank b where b.name = :name",
				Bank.class);
		query.setParameter("name", name);
		List<Bank> banks = query.getResultList();
		if (banks.isEmpty()) {
			return null;
		}
		return banks.get(0);
	}

	@Override
	public List<Bank> findBanks(Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bank> criteriaQuery = criteriaBuilder
				.createQuery(Bank.class);
		Root<Bank> root = criteriaQuery.from(Bank.class);
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("active"),
					active);
			criteriaQuery.where(predicate);
		}
		criteriaQuery.select(root)
				.orderBy(criteriaBuilder.asc(root.get("name")));
		TypedQuery<Bank> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

}
