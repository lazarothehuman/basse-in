package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import mz.co.basse.inbasse.accesscontrol.core.dao.DocumentNumerationDao;
import mz.co.basse.inbasse.accesscontrol.core.model.DocumentNumeration;
import mz.co.basse.inbasse.accesscontrol.core.model.DocumentNumerationType;

@Repository(value = "documentNumerationDao")
public class DocumentNumerationJpaDao implements DocumentNumerationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public DocumentNumeration findDocumentNumeration(
			DocumentNumerationType type) {
		TypedQuery<DocumentNumeration> query = entityManager.createQuery(
				"select documentNumeration from DocumentNumeration documentNumeration "
						+ "where documentNumeration.type = :type",
				DocumentNumeration.class);
		query.setParameter("type", type);
		List<DocumentNumeration> documentNumerations = query.getResultList();
		if (documentNumerations.isEmpty()) {
			return null;
		}
		return documentNumerations.get(0);
	}

	@Override
	public DocumentNumeration findDocumentNumeration(
			DocumentNumerationType type, int year) {
		TypedQuery<DocumentNumeration> query = entityManager.createQuery(
				"select documentNumeration from DocumentNumeration documentNumeration "
						+ "where documentNumeration.type = :type "
						+ "and documentNumeration.year = :year",
				DocumentNumeration.class);
		query.setParameter("type", type);
		query.setParameter("year", year);
		List<DocumentNumeration> documentNumerations = query.getResultList();
		if (documentNumerations.isEmpty()) {
			return null;
		}
		return documentNumerations.get(0);
	}

	@Override
	public void createOrUpdate(DocumentNumeration documentNumeration) {
		entityManager.merge(documentNumeration);
	}

}
