package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.Client;

public interface ClientDao {

	void create(Client client);
	
	List<Client> findClients(String name, String phone, Boolean active);
	
	void update(Client client);
	
}
