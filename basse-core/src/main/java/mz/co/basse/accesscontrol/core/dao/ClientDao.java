package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.Client;

public interface ClientDao {

	void create(Client client);
	
	List<Client> findClients(String name, String phone, Boolean active);
	
	void update(Client client);

	Client findClient(Long id);

	List<Client> findClients(Long id, String email, String name, String phone, boolean active);
	
}
