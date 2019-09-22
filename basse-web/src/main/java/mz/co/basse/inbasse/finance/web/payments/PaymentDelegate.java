package mz.co.basse.inbasse.finance.web.payments;

import java.text.DateFormat;
import java.util.Locale;

public class PaymentDelegate {

	private static final DateFormat dateFormat = DateFormat
			.getDateInstance(DateFormat.FULL, new Locale("pt"));

//	public static void printInvoice(Payment payment, Company company)
//			throws PrinterException, IOException, PrintException {

//		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
//		Map<String, Object> reportObject = new HashMap<String, Object>();
//		reportObject.put("formOfPayment",
//				Labels.getLabel(payment.getFormOfPayment().getMessageKey()));
//		reportObject.put("bank",
//				payment.getBank() != null ? payment.getBank().getName() : "");
//		reportObject.put("check", payment.getChequeNumber());
//		reportObject.put("value",
//				FormatUtils.getCurrencyFormat().format(payment.getValue()));
//		reportObjects.add(reportObject);
//
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("REPORT_NAME", Labels.getLabel("receipt.record"));
//		parameters.put("companyData",
//				Labels.getLabel("invoice.company.data",
//						new Object[] { company.getAddress(), company.getPhone(),
//								company.getEmail(), company.getNuit() }));
//		parameters.put("clientName", payment.getClient().getName());
//		parameters.put("clientNuit", payment.getClient().getNuit());
//		parameters.put("clientAddress", payment.getClient().getAddress());
//
//		parameters.put("receiptNumber", Labels.getLabel("report.receipt.number",
//				new Object[] { payment.getReceiptNumber() }));
//		parameters.put("date", dateFormat.format(payment.getDate()));
//		parameters.put("user", payment.getUser().getName());
//		parameters.put("valueInWords",
//				FormatUtils.numberInWords(payment.getValue()));
//
//		Report report = new Report(parameters, reportObjects, "receipt.jasper",
//				SessionHelper.getUser(), company);
//		UIHelper.generateReport(report, "recibo.pdf");
//	}
}
