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

import mz.co.basse.accesscontrol.core.dao.ProductDao;
import mz.co.basse.accesscontrol.core.model.Product;

@Repository(value = "productDao")
public class ProductJpaDao implements ProductDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void create(Product product) {
		entityManager.persist(product);
	}

	@Override
	public List<Product> findProducts(String name, Boolean limited, Boolean active) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> product = criteriaQuery.from(Product.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(active!=null) {
			Predicate predicate = criteriaBuilder.equal(product.get("active"), active);
			predicates.add(predicate);
		}
		
		if(name!=null) {
			Predicate predicate = criteriaBuilder.like(product.<String> get("name"), "%"+name+"%");
			predicates.add(predicate);
		}
		
		if(limited!=null) {
			Predicate predicate = criteriaBuilder.equal(product.get("limited"), limited);
			predicates.add(predicate);
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		criteriaQuery.select(product).orderBy(criteriaBuilder.asc(product.get("name")));
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public void update(Product product) {
		entityManager.merge(product);
	}

}
