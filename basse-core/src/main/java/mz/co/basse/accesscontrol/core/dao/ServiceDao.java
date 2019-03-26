package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.Service;

public interface ServiceDao {
	
	void create(Service service);
	
	List<Service> findServices(String name);
	
	void update(Service service);
	
}
