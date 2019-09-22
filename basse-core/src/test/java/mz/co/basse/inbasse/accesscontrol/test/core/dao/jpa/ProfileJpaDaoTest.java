package mz.co.basse.inbasse.accesscontrol.test.core.dao.jpa;

import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.dao.ProfileDao;
import mz.co.basse.inbasse.accesscontrol.core.dao.TransactionDao;
import mz.co.basse.inbasse.accesscontrol.core.model.Profile;
import mz.co.basse.inbasse.test.core.DBUnitTestCase;

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
public class ProfileJpaDaoTest extends DBUnitTestCase {

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private ProfileDao profileDao;

	@Test
	@Rollback(value = false)
	public void testCreateProfile() {
		Profile profile = new Profile();
		profile.setName("Administrador de Informática");
		profile.setTransactions(transactionDao.findTransactions(true));
		profileDao.create(profile);
		Assert.assertNotNull(profile.getId());
		Assert.assertTrue(profile.isActive());
	}

	@Test(expected = JpaSystemException.class)
	public void testCreateDuplicatedProfile() {
		Profile profile = new Profile();
		profile.setName("Gestor de Recursos Humanos");
		profile.setTransactions(transactionDao.findTransactions(true));
		profileDao.create(profile);
	}

	@Test
	@Rollback(value = false)
	public void testUpdateProfile() {
		List<Profile> activeProfiles = profileDao.findProfiles(true);
		Assert.assertEquals(2, activeProfiles.size());
		Profile profile = activeProfiles.get(0);
		profile.setActive(false);
		profileDao.update(profile);
		activeProfiles = profileDao.findProfiles(true);
		Assert.assertEquals(1, activeProfiles.size());
	}

	@Test
	public void testFindActiveProfiles() {
		List<Profile> profiles = profileDao.findProfiles(Boolean.TRUE);
		Assert.assertEquals(2, profiles.size());
	}

	@Test
	public void testFindInactiveProfiles() {
		List<Profile> profiles = profileDao.findProfiles(false);
		Assert.assertEquals(1, profiles.size());
	}

	@Test
	public void testFindProfiles() {
		List<Profile> profiles = profileDao.findProfiles(null);
		Assert.assertEquals(3, profiles.size());
	}

	@Test
	public void testFindProfileByName() {
		Profile profile = profileDao.findProfile("Gestor de Recursos Humanos");
		Assert.assertNotNull(profile);
		Assert.assertEquals(1L, profile.getId().longValue());
	}

	@Test
	public void testFindNonExistingProfileByName() {
		Assert.assertNull(profileDao
				.findProfile("Gestor de Recursos Humanos 2"));
	}

	@Override
	protected String getDataSetFileName() {
		return "ProfileJpaDaoTest.xml";
	}
}
