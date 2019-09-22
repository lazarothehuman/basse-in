package mz.co.basse.inbasse.finance.web.payments;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.finance.core.manager.FinanceManager;
import mz.co.basse.inbasse.finance.core.model.Bank;
import mz.co.basse.inbasse.finance.core.model.FormOfPayment;
import mz.co.basse.inbasse.finance.core.model.Payment;
import mz.co.basse.inbasse.web.util.AutoClosableController;
import mz.co.basse.inbasse.web.util.Queues;
import mz.co.basse.inbasse.web.util.SessionHelper;
import mz.co.basse.inbasse.web.util.UIHelper;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddPaymentController extends AutoClosableController {

	private static final long serialVersionUID = 9082407966154568550L;

	@Wire
	private Datebox date;

	@Wire
	private Combobox formOfPayment, bank;

	@Wire
	private Textbox check;

	@Wire
	private Decimalbox value;

//	@Wire
//	Textbox clientDescription;
	
	@Wire
	public
	Textbox clientDescription;

	@WireVariable
	private FinanceManager financeManager;

	@WireVariable
	private AccessControlManager accessControlManager;

	private EventQueue<Event> clientSelectionQueue;

	private EventListener<Event> clientSelectionEventListener;

	private Client selectedClient;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Calendar calendar = Calendar.getInstance();
		date.setValue(calendar.getTime());
		UIHelper.buildFormOfPaymentCombobox(formOfPayment);
		UIHelper.buildBankCombobox(financeManager.findBanks(true), bank);
	}

	@Listen("onClick = #saveButton")
	public void save() throws PrinterException, IOException, PrintException {
		if (selectedClient != null) {
			FormOfPayment selectedFormOfPayment = formOfPayment
					.getSelectedItem().getValue();
			Bank selectedBank = (Bank) UIHelper
					.nullSafeGetSelectedItemValue(bank);
			String checkNumber = check.getText();
			if (!selectedFormOfPayment.equals(FormOfPayment.CASH)
					&& selectedBank == null) {
				throw new WrongValueException(bank, Labels.getLabel(
						"bank.is.required.for.selected.form.of.payment"));
			}
			if (selectedFormOfPayment.equals(FormOfPayment.CHEQUE)
					&& StringUtils.isBlank(checkNumber)) {
				throw new WrongValueException(check, Labels.getLabel(
						"check.number.required.for.selected.form.of.payment"));
			}
			Payment payment = new Payment();
			payment.setClient(selectedClient);
			payment.setFormOfPayment(selectedFormOfPayment);
			payment.setBank(selectedBank);
			payment.setChequeNumber(checkNumber);
			payment.setValue(value.getValue());
			payment.setDate(date.getValue());
			payment.setUser(SessionHelper.getUser());
			if (selectedClient.getId() == null) {
				accessControlManager.createOrUpdateClient(selectedClient);
			}
			financeManager.createPayment(payment);
//			PaymentDelegate.printInvoice(payment,
//					accessControlManager.findCompany());
			close();
		} else {
			throw new WrongValueException(clientDescription,
					Labels.getLabel("select.client"));
		}
	}

	@Listen("onChange = #formOfPayment")
	public void toggleComponents() {
		FormOfPayment selectedFormOfPayment = formOfPayment.getSelectedItem()
				.getValue();
		bank.setSelectedItem(null);
		bank.setDisabled(selectedFormOfPayment.equals(FormOfPayment.CASH));
		check.setValue(null);
		check.setDisabled(!selectedFormOfPayment.equals(FormOfPayment.CHEQUE));
	}

	@Listen("onClick = #searchClient")
	public void searchClient() {		
		Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("addPaymentController", this);
		Window window = (Window)Executions.createComponents(
               "accesscontrol/clients/clients-list.zul", null, arguments);
		window.setTitle("Procurar Clientes");
		window.setHeight("75%");
		window.setWidth("90%");
		window.setClosable(true);
		window.doModal();		
	}
}
