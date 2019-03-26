package mz.co.basse.accesscontrol.test.core.manager;

import java.util.List;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.core.model.Language;
import mz.co.basse.test.core.DBUnitTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-core.xml",
		"classpath:applicationContext-test-dataAccess.xml",
		"classpath:applicationContext-tx.xml" })
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
//		Assert.assertNotNull(user.getId());
	}

	@Override
	protected String getDataSetFileName() {
		return "AccessControlManagerTest.xml";
	}
}