package mz.co.basse.accesscontrol.core.dao;

import java.util.Date;
import java.util.List;

import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.PaymentStatus;
import mz.co.basse.accesscontrol.core.model.Sale;
import mz.co.basse.accesscontrol.core.model.User;

public interface SaleDao {
	
	void create(Sale sale);

	Sale findRequest(String invoiceNumber);

	List<Sale> findRequests(Boolean active, Client client,
			User user, Date startDate, Date endDate, 
			List<PaymentStatus> paymentStatus, Integer resultsLimit);

	void update(Sale request);

	Sale findLastSales(int year);
	
}
