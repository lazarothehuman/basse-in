package mz.co.basse.inbasse.accesscontrol.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mz.co.basse.inbasse.accesscontrol.core.dao.UserDao;
import mz.co.basse.inbasse.accesscontrol.core.model.User;

import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class UserJpaDao implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(User user) {
		entityManager.persist(user);
	}

	public User findUser(String login) {
		TypedQuery<User> query = entityManager.createQuery(
				"select distinct user from User user "
						+ "join fetch user.profile profile "
						+ "left join fetch profile.transactions "
						+ "where login = :login "
						+ "and user.active = true", User.class);
		query.setParameter("login", login);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	public List<User> findUsers(Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder
				.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("active"),
					active);
			criteriaQuery.where(predicate);
		}
		criteriaQuery.select(root).orderBy(
				criteriaBuilder.asc(root.get("login")));
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public void update(User user) {
		entityManager.merge(user);
	}

}
