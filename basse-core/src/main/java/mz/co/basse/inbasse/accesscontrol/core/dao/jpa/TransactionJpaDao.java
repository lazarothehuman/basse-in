package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mz.co.basse.inbasse.accesscontrol.core.dao.TransactionDao;
import mz.co.basse.inbasse.accesscontrol.core.model.Transaction;

import org.springframework.stereotype.Repository;

@Repository(value = "transactionDao")
public class TransactionJpaDao implements TransactionDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(Transaction transaction) {
		entityManager.persist(transaction);
	}

	public List<Transaction> findTransactions(Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder
				.createQuery(Transaction.class);
		Root<Transaction> root = criteriaQuery.from(Transaction.class);
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("active"),
					active);
			criteriaQuery.where(predicate);
		}
		criteriaQuery.select(root).orderBy(
				criteriaBuilder.asc(root.get("name")));
		TypedQuery<Transaction> query = entityManager
				.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public void update(Transaction transaction) {
		entityManager.merge(transaction);
	}
	
	@Override
	public Transaction findTransaction(String code) {
		TypedQuery<Transaction> query = entityManager
				.createQuery(
						"select transaction from Transaction transaction "
								+ "where transaction.code = :code",
						Transaction.class);
		query.setParameter("code", code);
		List<Transaction> transactions = query.getResultList();
		if (transactions.isEmpty()) {
			return null;
		}
		return transactions.get(0);
	}
}
