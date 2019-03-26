package mz.co.basse.accesscontrol.test.core.dao.jpa;

import java.util.List;

import mz.co.basse.accesscontrol.core.dao.ProfileDao;
import mz.co.basse.accesscontrol.core.dao.UserDao;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.core.model.Language;
import mz.co.basse.test.core.DBUnitTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-core.xml",
		"classpath:applicationContext-test-dataAccess.xml",
		"classpath:applicationContext-tx.xml" })
@Transactional
public class UserJpaDaoTest extends DBUnitTestCase {

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private UserDao userDao;

	@Test
	@Rollback(value = false)
	public void testCreateUser() {
		User user = new User();
		user.setName("Irshad Ismael");
		user.setLanguage(Language.PORTUGUESE);
		user.setLogin("ismael");
		user.setPassword("linux");
		user.setProfile(profileDao.findProfiles(true).get(0));
		userDao.create(user);
		Assert.assertNotNull(user.getId());
		Assert.assertTrue(user.isActive());
	}

	@Test(expected = JpaSystemException.class)
	public void testCreateUserWithDuplicatedLogin() {
		User user = new User();
		user.setName("Irshad Ismael");
		user.setLanguage(Language.PORTUGUESE);
		user.setLogin("irshad");
		user.setPassword("linux");
		user.setProfile(profileDao.findProfiles(true).get(0));
		userDao.create(user);
	}

	@Test
	public void testUpdateUser() {
		User user = userDao.findUser("irshad");
		user.setLogin("outroLogin");
		userDao.update(user);
		Assert.assertNull(userDao.findUser("irshad"));
	}

	@Test
	public void testFindUserByLogin() {
		User user = userDao.findUser("irshad");
		Assert.assertEquals("Irshad Ismael", user.getName());
	}

	@Test
	public void testFindUserInexistentUserByLogin() {
		Assert.assertNull(userDao.findUser("Junior"));
	}

	@Test
	public void testFindActiveUsers() {
		List<User> users = userDao.findUsers(Boolean.TRUE);
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void testFindInactiveUsers() {
		List<User> users = userDao.findUsers(false);
		Assert.assertEquals(2, users.size());
	}

	@Test
	public void testFindUsers() {
		List<User> users = userDao.findUsers(null);
		Assert.assertEquals(3, users.size());
	}

	@Override
	protected String getDataSetFileName() {
		return "UserJpaDaoTest.xml";
	}
}
