package mz.co.basse.inbasse.accesscontrol.core.manager;

import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.Product;
import mz.co.basse.inbasse.accesscontrol.core.model.Profile;
import mz.co.basse.inbasse.accesscontrol.core.model.Request;
import mz.co.basse.inbasse.accesscontrol.core.model.Supplier;
import mz.co.basse.inbasse.accesscontrol.core.model.Transaction;
import mz.co.basse.inbasse.accesscontrol.core.model.User;

public interface AccessControlManager {
	//Transaction
	void createTransaction(Transaction transaction);

	List<Transaction> findTransactions();
	
	Transaction findTransaction(String code);
	
	//Profile
	void createOrUpdateProfile(Profile profile);

	void updateProfile(Profile profile);

	List<Profile> findProfiles(Boolean active);
	
	Profile findProfile(String name);

	//User
	void createOrUpdateUser(User user);

	List<User> findUsers(Boolean active);

	User findUser(String login);

	void encryptUserPassword(User user);

	boolean passwordMatches(String plainPassword, String encryptedPassword);

	//Client
	void createOrUpdateClient(Client client);
	
	
	public String nextBarcode();

	List<Client> findClients(Long id, String email, String name, String phone, boolean active);

	Client findClient(Long id);

	Product findProduct(Long id);

	void createRequest(Request request);

	void createSupplier(Supplier supplier);

	List<Supplier> findSuppliers(Long id, String name, String email, String address, String  phone, boolean active);

	void updateSupplier(Supplier supplier);

	void createProduct(Product product);
	

}
