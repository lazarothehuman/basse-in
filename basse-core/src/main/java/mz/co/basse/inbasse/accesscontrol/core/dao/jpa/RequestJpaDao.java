package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mz.co.basse.inbasse.accesscontrol.core.dao.RequestDao;
import mz.co.basse.inbasse.accesscontrol.core.model.Request;

@Repository(value = "requestDao")
public class RequestJpaDao implements RequestDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public void create(Request request) {
		entityManager.persist(request);
	}

}
