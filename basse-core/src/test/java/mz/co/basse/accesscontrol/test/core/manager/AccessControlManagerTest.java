package mz.co.basse.accesscontrol.test.core.manager;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.Levy;
import mz.co.basse.accesscontrol.core.model.LevyItem;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.accesscontrol.core.model.ProductPrice;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.Request;
import mz.co.basse.accesscontrol.core.model.RequestItem;
import mz.co.basse.accesscontrol.core.model.Technician;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.core.model.Language;
import mz.co.basse.test.core.DBUnitTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-core.xml",
		"classpath:applicationContext-test-dataAccess.xml", "classpath:applicationContext-tx.xml" })
public class AccessControlManagerTest extends DBUnitTestCase {

	@Autowired
	private AccessControlManager accessControlManager;

	@Test
	@Rollback(value = false)
	public void testCreateUser() {
		User user = new User();
		user.setName("Lazaro Mathe Jr.");
		user.setLogin("mathe");
		user.setPassword("linux");
		List<Profile> profiles = accessControlManager.findProfiles(true);
		user.setProfile(profiles.get(0));
		user.setLanguage(Language.PORTUGUESE);
		accessControlManager.createOrUpdateUser(user);
		// Assert.assertNotNull(user.getId());
	}
	
	@Test
	public void testFindClientById() {
		Client client = accessControlManager.findClient(1l);
		Assert.assertNotNull(client);
		Assert.assertTrue(client.getName().contains("Lazaro"));
	}
	
	@Test
	public void testFindProducutById() {
		Product product = accessControlManager.findProduct(1l);
		Assert.assertNotNull(product);
		Assert.assertTrue(product.getName().contains("Camera"));
	}
	
	@Test
	@Rollback(value=false)
	public void testCreateRequest() {
		Client client = accessControlManager.findClient(1l);
		Product product = accessControlManager.findProduct(1l);
		Request request = new Request();
		request.setClient(client);
		request.setRequestDate(new Date());
		request.setAdditionalInformation("Teste 1 de request");
		
		//adding an item
		RequestItem item = new RequestItem();
		item.setRequest(request);
		item.setQuantity(2);
		item.setProduct(product);
		request.addItem(item);
		
		accessControlManager.createRequest(request);
		//Assert.assertNotNull(request.getId());
		//Assert.assertNotNull(item.getId());
		//Assert.assertEquals(1, request.getItems().size());
	}

	@Test
	@Rollback(value=false)
	public void testCreateLevy() {
//		Technician technician = accessControlManager.findTechnician(1l, null, null, null, null, true);
//		ProductPrice product = accessControlManager.findProductPrice(1l, null, null,true)l
//		Levy levy = new Levy();
//		levy.setOccuranceDate(new Date());
//		levy.setRequest(request);
//		levy.setNotes("Teste 1");
//		LevyItem item = new LevyItem();
//		item.setLevy(levy);
//		item.setQuantity(2);
//		item.setProduct(product);
//		levy.addItem(item);
//		levy.addTechnician(technician);
//		accessControlManager.createLevy(levy);
	}

	@Override
	protected String getDataSetFileName() {
		return "AccessControlManagerTest.xml";
	}
}