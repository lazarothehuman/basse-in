package mz.co.basse.accesscontrol.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mz.co.basse.accesscontrol.core.dao.ProfileDao;
import mz.co.basse.accesscontrol.core.model.Profile;

import org.springframework.stereotype.Repository;

@Repository(value = "ProfileDao")
public class ProfileJpaDao implements ProfileDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(Profile profile) {
		entityManager.persist(profile);
	}

	public List<Profile> findProfiles(Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Profile> criteriaQuery = criteriaBuilder
				.createQuery(Profile.class);
		Root<Profile> root = criteriaQuery.from(Profile.class);
		root.fetch("transactions");
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("active"),
					active);
			criteriaQuery.where(predicate);
		}
		criteriaQuery.select(root).distinct(true)
				.orderBy(criteriaBuilder.asc(root.get("name")));
		TypedQuery<Profile> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public void update(Profile profile) {
		entityManager.merge(profile);
	}

	public Profile findProfile(String name) {
		TypedQuery<Profile> query = entityManager.createQuery(
				"select profile from Profile profile where name = :name",
				Profile.class);
		query.setParameter("name", name);
		List<Profile> profiles = query.getResultList();
		if (profiles.isEmpty()) {
			return null;
		}
		return profiles.get(0);
	}

}
