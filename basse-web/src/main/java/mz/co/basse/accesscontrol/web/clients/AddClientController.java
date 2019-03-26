package mz.co.basse.accesscontrol.web.clients;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.web.util.AutoClosableController;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddClientController extends AutoClosableController {

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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	

	@Listen("onClick = #saveButton")
	public void save() {

		Client client = new Client();
		client.setName(name.getText().trim());
		client.setAddress(address.getText().trim());
		client.setEmail(email.getText().trim());
		client.setPhone(phone.getText());
		accessControlManager.createOrUpdateClient(client);
		close();
	}

}
