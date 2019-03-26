package mz.co.basse.finance.web.payments;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.print.PrintException;

import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Branch;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.core.exception.PrepaidPaymentCanNotBeCanceledException;
import mz.co.basse.core.model.Internationalized;
import mz.co.basse.finance.core.manager.FinanceManager;
import mz.co.basse.finance.core.model.Payment;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PaymentsListingController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -3962427951112469628L;

	@Wire
	private Checkbox showInactives;

	@Wire
	private Combobox client, branch;

	@Wire
	private Textbox receipt;

	@Wire
	private Datebox startDate, endDate;

	@Wire
	private Listbox paymentsList;

	@WireVariable
	private FinanceManager financeManager;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		User user = SessionHelper.getUser();
//		UIHelper.buildClientComboboxWithContainsFilter(accessControlManager
//				.findClients(null, null, null, null, true, null, null), client);
		startDate.setValue(mz.co.basse.web.util.DateUtils.getPastDate(14));
		endDate.setValue(mz.co.basse.web.util.DateUtils.getCurrentDate());
	}

	@Listen("onClick = #searchButton")
	public void updateGrid() {
		Client selectedClient = (Client) UIHelper
				.nullSafeGetSelectedItemValue(client);
		List<Payment> payments = financeManager.findPayments(selectedClient,
				null, UIHelper.nullIfEmpty(receipt.getValue()),
				startDate.getValue(), endDate.getValue(),
				!showInactives.isChecked());
		paymentsList.setModel(new ListModelList<Payment>(payments));
	}

	@Listen("onClick = #addPayment")
	public void addPayment() {
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.payment"),
				"finance/payments/add-payment.zul");
	}

	@Listen("onClick = #print")
	public void printInvoice()
			throws PrinterException, IOException, PrintException {
		Payment selectedPayment = getSelectedPayment();
//		if (selectedPayment.isPrepaid()) {
//			InvoiceDelegate.print(
//					accessControlManager.findCargoTransferRequest(
//							selectedPayment.getCargoTransferRequest().getId()),
//					company, false, false, false);
//		} else {
//			PaymentDelegate.printInvoice(selectedPayment, company);
//		}
	}

	@Listen("onClick = #cancel")
	public void cancel() {
		final Payment selectedPayment = getSelectedPayment();
		Messagebox.show(Labels.getLabel("inactivate.payment.question"),
				Labels.getLabel("confirmation"),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					public void onEvent(Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (!selectedPayment.isActive()) {
								throw new WrongValueException(Labels.getLabel(
										"payment.can.not.cancel.already.canceled"));
							}
							try {
//								financeManager.cancelPayment(selectedPayment);
								updateGrid();
							} catch (PrepaidPaymentCanNotBeCanceledException e) {
								throw new WrongValueException(Labels.getLabel(
										"prepaid.payment.can.not.be.canceled"));
							}
						}
					}
				});
	}

	public String formatBoolean(Boolean value) {
		return FormatUtils.formatBoolean(value);
	}

	public String formatDate(Date date) {
		return FormatUtils.getDateformat().format(date);
	}

	public String formatCurrency(BigDecimal value) {
		if (value != null && BigDecimal.ZERO.compareTo(value) != 0) {
			return FormatUtils.getCurrencyFormat().format(value);
		} else {
			return "";
		}
	}

	public String internationalize(Internationalized internationalized) {
		return Labels.getLabel(internationalized.getMessageKey());
	}

	private Payment getSelectedPayment() {
		Listitem selectedItem = paymentsList.getSelectedItem();
		if (selectedItem != null) {
			Payment selectedPayment = selectedItem.getValue();
			return selectedPayment;
		} else {
			throw new WrongValueException(Labels.getLabel("select.payment"));
		}
	}
}
