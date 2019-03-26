package mz.co.basse.accesscontrol.web.clients;

import java.util.List;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.finance.web.payments.AddPaymentController;
import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ClientsListingController extends AutoClosableController {

	private static final long serialVersionUID = -5748966643895131995L;

	private static final int RESULTS_LIMIT = 100;

	@Wire
	private Listbox clientsList;

	@Wire
	private Textbox name;

	@Wire
	private Textbox nuit;

	@Wire
	private Textbox phone;

	@Wire
	private Listheader passengerColumn;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Checkbox contractual, showInactives;

	@Wire
	private Button selectButton, addButton, inactivateButton;
	
	Window clientsListWindow;
	
	AddPaymentController addPaymentController;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		addPaymentController = (AddPaymentController) Executions.getCurrent().getArg().get("AddPaymentController");
	}

	@Listen("onClick = #searchButton")
	public void search() {
		updateGrid(null);
	}
	public void updateGrid(String typedName) {
		UIHelper.clearListbox(clientsList);
		List<Client> clients = null;
//		List<Client> clients = accessControlManager.findClients(name.getText(),
//					nuit.getText(), phone.getText(),
//					contractual.isChecked() ? true : null,
//					!showInactives.isChecked(), null, RESULTS_LIMIT);

		if (clients.size() == RESULTS_LIMIT) {
			Messagebox.show(Labels.getLabel("search.was.limited"));
		}
		for (Client client : clients) {
			Listitem listitem = new Listitem();
			listitem.setValue(client);
			listitem.appendChild(new Listcell(client.getName()));
			listitem.appendChild(new Listcell(client.getAddress()));
			listitem.appendChild(new Listcell(client.getPhone()));
			listitem.appendChild(new Listcell(client.getEmail()));
			listitem.appendChild(
					new Listcell(FormatUtils.formatBoolean(client.isActive())));
			clientsList.appendChild(listitem);
		}
		UIHelper.setRecordsNumber(clients.size(), clientsList);
	}

	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.client"),
				"accesscontrol/clients/add-client.zul");
	}

	@Listen("onDoubleClick = #clientsList")
	public void modify() {
		Client selectedClient = getSelectedClient();
		SessionHelper.setObject("selectedClient", selectedClient);
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("modify.client"),
				"accesscontrol/clients/modify-client.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		final Client client = getSelectedClient();
		Messagebox.show(Labels.getLabel("inactivate.client.question"),
				Labels.getLabel("confirmation"),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							client.setActive(!client.isActive());
//							accessControlManager.createOrUpdateClient(client);
							updateGrid(null);
						}
					}
				});
	}

	private Client getSelectedClient() {
		Listitem selectedItem = clientsList.getSelectedItem();
		if (selectedItem != null) {
			return selectedItem.getValue();
		} else {
			throw new WrongValueException(Labels.getLabel("select.client"));
		}
	}

	@Listen("onClick = #selectButton")
	public void select() {
		Client selectedClient = getSelectedClient();
		SessionHelper.setObject("addPaymentController", selectedClient);
		if(addPaymentController!=null) {
			addPaymentController.clientDescription.setText(selectedClient.getName());
		}
		
		
		clientsListWindow.detach();
	}
}