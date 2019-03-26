package mz.co.basse.accesscontrol.core.manager;

import java.util.Calendar;
import java.util.List;

import mz.co.basse.accesscontrol.core.dao.ClientDao;
import mz.co.basse.accesscontrol.core.dao.DocumentNumerationDao;
import mz.co.basse.accesscontrol.core.dao.ProductDao;
import mz.co.basse.accesscontrol.core.dao.ProfileDao;
import mz.co.basse.accesscontrol.core.dao.TransactionDao;
import mz.co.basse.accesscontrol.core.dao.UserDao;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.DocumentNumeration;
import mz.co.basse.accesscontrol.core.model.DocumentNumerationType;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.Transaction;
import mz.co.basse.accesscontrol.core.model.User;

import org.apache.commons.lang.StringUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.krysalis.barcode4j.impl.upcean.UPCEANLogicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "accessControlManager")
public class AccessControlManagerImpl implements AccessControlManager {

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private DocumentNumerationDao documentNumerationDao;

	public void createTransaction(Transaction transaction) {
		transactionDao.create(transaction);
	}

	public List<Profile> findProfiles(Boolean active) {
		return profileDao.findProfiles(active);
	}

	public List<Transaction> findTransactions() {
		return transactionDao.findTransactions(true);
	}
	
	public Transaction findTransaction(String code) {
		return transactionDao.findTransaction(code);
	}

	public void createOrUpdateProfile(Profile profile) {
		if (profile.getId() == null) {
			profileDao.create(profile);
		} else {
			profileDao.update(profile);
		}
	}

	public Profile findProfile(String name) {
		return profileDao.findProfile(name);
	}

	public void createOrUpdateUser(User user) {
		if (user.getId() == null) {
			userDao.create(user);
		} else {
			userDao.update(user);
		}
	}

	public List<User> findUsers(Boolean active) {
		return userDao.findUsers(active);
	}

	public User findUser(String login) {
		return userDao.findUser(login);
	}

	public void encryptUserPassword(User user) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		String encryptedPassword = encryptor
				.encryptPassword(user.getPassword());
		user.setPassword(encryptedPassword);
	}

	public boolean passwordMatches(String plainPassword,
			String encryptedPassword) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		return encryptor.checkPassword(plainPassword, encryptedPassword);
	}

	public void updateProfile(Profile profile) {
		profileDao.update(profile);
	}
	
	//Client
	public void createOrUpdateClient(Client client) {
		if(client.getId() == null) {
			clientDao.create(client);
		} else {
			clientDao.update(client);
		}
	}
	
	public String nextBarcode() {
		String number = nextNumber(DocumentNumerationType.BAR_CODE);
		String code = StringUtils.leftPad(String.valueOf(number), 10, "0");
		return code + UPCEANLogicImpl.calcChecksum(code);
	}
	
	
	public String nextNumber(DocumentNumerationType type) {
		DocumentNumeration documentNumeration;
		if (type.isAnual()) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			documentNumeration = documentNumerationDao
					.findDocumentNumeration(type, year);
		} else {
			documentNumeration = documentNumerationDao
					.findDocumentNumeration(type);
		}
		if (documentNumeration == null) {
			documentNumeration = new DocumentNumeration(type);
		}
		String number = documentNumeration.next();
		documentNumerationDao.createOrUpdate(documentNumeration);
		return number;
	}

	@Override
	public void createOrUpdateProduct(Product product) {
		if(product.getId()==null) {
			productDao.create(product);
		} else {
			productDao.update(product);
		}
		
	}

	@Override
	public List<Product> findProducts(String name, Boolean limited, Boolean active) {
		return productDao.findProducts(name, limited, active);
	}
	
	
}
