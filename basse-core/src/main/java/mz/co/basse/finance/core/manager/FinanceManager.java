package mz.co.basse.finance.core.manager;

import java.util.Date;
import java.util.List;


import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.finance.core.model.Bank;
import mz.co.basse.finance.core.model.Payment;
import mz.co.basse.finance.core.model.Sale;

public interface FinanceManager {

	void createBank(Bank bank);

	void updateBank(Bank bank);

	Bank findBank(String name);

	List<Bank> findBanks(Boolean active);

	void createPayment(Payment payment);

	void cancelPayment(Payment payment);

	List<Payment> findPayments(Client client, User user, String receiptNumber, 
			Date startDate, Date endDate, Boolean active);

	Payment findPayment(Sale sale);

//	void updateClientBalance(Client client);

//	void createCreditNote(CreditNote creditNote);
//
//	void createDebitNote(DebitNote debitNote);
//
//	List<CreditNote> findCreditNotes(Date startDate, Date endDate,
//			Client client, String number, String description);
//
//	List<DebitNote> findDebitNotes(Date startDate, Date endDate, Client client,
//			String number, String description);
//
//	List<ClientFinancialActivityHelper> findFinancialActivities(Date startDate,
//			Date endDate, List<CargoTransferRequest> requests,
//			List<Payment> payments);
//
//	void createVatExemptionReason(VatExemptionReason reason);
//
//	void updateVatExemptionReason(VatExemptionReason reason);
//
//	List<VatExemptionReason> findVatExemptionReasons(Boolean active);
//
//	List<VatExemptionReason> findVatExemptionReasons(String genericText,
//			Boolean active);
//
//	List<MontlyBalanceHelper> findMontlyBalances(Client client);
//
//	List<MontlyBalanceHelper> calculateAccumulatedBalances(String clientName,
//			Long clientId, List<MontlyBalanceHelper> balances, Date startMonth,
//			Date endMonth);
}
