package mz.co.basse.accesscontrol.core.dao.jpa;

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

import mz.co.basse.accesscontrol.core.dao.ClientDao;
import mz.co.basse.accesscontrol.core.model.Client;

@Repository(value = "clienteDao")
public class ClientJpaDao implements ClientDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(Client client) {
		entityManager.persist(client);
	}

	@Override
	public void update(Client client) {
		entityManager.merge(client);
	}

	@Override
	public List<Client> findClients(String name, String phone, Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> root = criteriaQuery.from(Client.class);
		List<Predicate> predicates = new ArrayList<>();
		if(name != null) {
			Predicate predicate = criteriaBuilder.like(root.<String> get("name"), "%"+name+"%");
			predicates.add(predicate);
		}
		if(phone != null) {
			Predicate predicate = criteriaBuilder.equal(root.<String> get("phone"), phone);
			predicates.add(predicate);
		}
		if(active != null) {
			Predicate predicate = criteriaBuilder.equal(root.<String> get("active"), active);
			predicates.add(predicate);
		}
		criteriaQuery.select(root)
			.where(predicates.toArray(new Predicate[predicates.size()]))
			.orderBy(criteriaBuilder.asc(root.get("name")));
		TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
