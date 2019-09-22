package mz.co.basse.inbasse.finance.core.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.co.basse.inbasse.accesscontrol.core.dao.ClientDao;
import mz.co.basse.inbasse.accesscontrol.core.dao.SaleDao;
import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.core.exception.DuplicateEntryException;
import mz.co.basse.inbasse.finance.core.dao.BankDao;
import mz.co.basse.inbasse.finance.core.dao.PaymentDao;
import mz.co.basse.inbasse.finance.core.model.Bank;
import mz.co.basse.inbasse.finance.core.model.Payment;
import mz.co.basse.inbasse.finance.core.model.Sale;


@Service(value = "financeManager")
public class FinanceManagerImpl implements FinanceManager {

	@Autowired
	private BankDao bankDao;

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private AccessControlManager accessControlManager;

	@Autowired
	private SaleDao saleDao;

//	@Autowired
//	private CreditNoteDao creditNoteDao;
//
//	@Autowired
//	private DebitNoteDao debitNoteDao;
//
//	@Autowired
//	private VatExemptionReasonDao vatExemptionReasonDao;

	@Override
	public void createBank(Bank bank) {
		if (bankDao.findBank(bank.getName()) == null) {
			bankDao.createBank(bank);
		} else {
			throw new DuplicateEntryException(
					"Já existe Banco com o nome " + bank.getName());
		}
	}

	@Override
	public void updateBank(Bank bank) {
		Bank existing = bankDao.findBank(bank.getName());
		if (existing != null && !existing.getId().equals(bank.getId())) {
			throw new DuplicateEntryException(
					"Já existe Banco com nome " + bank.getName());
		} else {
			bankDao.update(bank);
		}
	}

	@Override
	public Bank findBank(String name) {
		return bankDao.findBank(name);
	}

	@Override
	public List<Bank> findBanks(Boolean active) {
		return bankDao.findBanks(active);
	}

	@Override
	public void createPayment(Payment payment) {
//		String receiptNumber = cargoManager
//				.nextNumber(DocumentNumerationType.RECEIPT);
//		payment.setReceiptNumber(receiptNumber);
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
//		payment.setCreationDate(now);
		paymentDao.createPayment(payment);
//		if (!payment.isPrepaid()) {
//			Client client = payment.getClient();
//			client.setUpdateBalance(true);
//			accessControlManager.createOrUpdateClient(client);
//		}
	}

	public void cancelPayment(Payment payment) {
		payment.setActive(false);
		paymentDao.updatePayment(payment);
		Client client = payment.getClient();
//		client.setUpdateBalance(true);
		clientDao.update(client);
	}

	@Override
	public List<Payment> findPayments(Client client, User user,	String receiptNumber,
			Date startDate, Date endDate, Boolean active) {
		return paymentDao.findPayments(client, user, receiptNumber,
				startDate, endDate, active);
	}

	@Override
	public Payment findPayment(Sale sale) {
		return paymentDao.findPayment(sale);
	}
}
