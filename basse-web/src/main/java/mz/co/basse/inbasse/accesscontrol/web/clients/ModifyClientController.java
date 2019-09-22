package mz.co.basse.inbasse.accesscontrol.web.clients;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.web.util.AutoClosableController;
import mz.co.basse.inbasse.web.util.SessionHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModifyClientController extends AutoClosableController {

	private static final long serialVersionUID = -3408717958305588442L;

	@Wire
	private Textbox name;

	@Wire
	private Textbox address;

	@Wire
	private Textbox phone;

	@Wire
	private Textbox email;

	@WireVariable
	private AccessControlManager accessControlManager;

	private Client selectedClient;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		selectedClient = (Client) SessionHelper.takeObject("selectedClient");
		name.setText(selectedClient.getName());
		address.setText(selectedClient.getAddress());
		phone.setText(selectedClient.getPhone());
		email.setText(selectedClient.getEmail());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		selectedClient.setName(name.getText());
		selectedClient.setAddress(address.getText());
		selectedClient.setPhone(phone.getText());
		selectedClient.setEmail(email.getText());
		
//		accessControlManager.createOrUpdateClient(selectedClient);
		close();
	}

}
