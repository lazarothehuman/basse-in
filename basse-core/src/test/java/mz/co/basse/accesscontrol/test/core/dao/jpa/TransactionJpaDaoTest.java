package mz.co.basse.accesscontrol.test.core.dao.jpa;

import java.util.List;

import mz.co.basse.accesscontrol.core.dao.TransactionDao;
import mz.co.basse.accesscontrol.core.model.Transaction;
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
public class TransactionJpaDaoTest extends DBUnitTestCase {

	@Autowired
	private TransactionDao transactionDao;

	@Test
	@Rollback(value = false)
	public void testCreateTransaction() {
		Transaction transaction = new Transaction();
		transaction.setCode("004");
		transaction.setName("Cadastrar Local");
		transactionDao.create(transaction);
		Assert.assertNotNull(transaction.getId());
		Assert.assertTrue(transaction.isActive());
	}

	@Test(expected = JpaSystemException.class)
	public void testCreateTransactionWithDuplicatedCode() {
		Transaction transaction = new Transaction();
		transaction.setCode("001");
		transaction.setName("Cadastrar Local");
		transaction.setActive(true);
		transactionDao.create(transaction);
	}

//	@Test(expected = JpaSystemException.class)
//	public void testCreateTransactionWithDuplicatedName() {
//		Transaction transaction = new Transaction();
//		transaction.setCode("004");
//		transaction.setName("Cadastrar Transacções");
//		transaction.setActive(true);
//		transactionDao.create(transaction);
//	}

	@Test
	@Rollback(value = false)
	public void testUpdateTransaction() {
		List<Transaction> activeTransactions = transactionDao
				.findTransactions(true);
		Assert.assertEquals(2, activeTransactions.size());
		Transaction transaction = activeTransactions.get(0);
		transaction.setActive(false);
		transactionDao.update(transaction);
		activeTransactions = transactionDao.findTransactions(true);
		Assert.assertEquals(1, activeTransactions.size());
	}

	@Test
	@Rollback(value = false)
	public void testFindTransactions() {
		List<Transaction> transactions = transactionDao.findTransactions(null);
		Assert.assertEquals(3, transactions.size());
	}

	@Test
	@Rollback(value = false)
	public void testFindActiveTransactions() {
		List<Transaction> transactions = transactionDao
				.findTransactions(Boolean.TRUE);
		Assert.assertEquals(2, transactions.size());
	}

	@Test
	@Rollback(value = false)
	public void testFindInactiveTransactions() {
		List<Transaction> transactions = transactionDao
				.findTransactions(Boolean.FALSE);
		Assert.assertEquals(1, transactions.size());
	}

	@Override
	protected String getDataSetFileName() {
		return "TransactionJpaDaoTest.xml";
	}

}
