package mz.co.basse.inbasse.finance.core.dao;

import java.util.Date;
import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.finance.core.model.Payment;
import mz.co.basse.inbasse.finance.core.model.Sale;


public interface PaymentDao {

	void createPayment(Payment payment);

	void updatePayment(Payment payment);

	List<Payment> findPayments(Client client, User user, String receiptNumber,
			Date startDate, Date endDate, Boolean active);

	Payment findPayment(Sale sale);

}
