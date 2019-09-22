package mz.co.basse.inbasse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.model.Product;

public interface ProductDao {
	
	void create(Product product);
	
	List<Product> findProducts(String name, Boolean limited, Boolean active);
	
	void update(Product product);

	Product findProduct(Long id);
	
}
