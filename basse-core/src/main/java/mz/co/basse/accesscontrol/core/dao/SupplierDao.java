package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.Supplier;

public interface SupplierDao {

	void create(Supplier supplier);

	List<Supplier> find(Long id, String name, String email, String address, String phone, boolean active);

	void update(Supplier supplier);

}