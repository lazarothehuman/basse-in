package mz.co.basse.accesscontrol.web.products;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Client;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.SessionHelper;

@VariableResolver(DelegatingVariableResolver.class)
public class ModifyProductController  extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6225545814419720637L;

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

	private Product selectedPoduct;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		selectedPoduct = (Product) SessionHelper.takeObject("selectedPoduct");
		name.setText(selectedPoduct.getName());
//		address.setText(selectedPoduct.getPricePerUnit());
//		phone.setText(selectedPoduct.isLimited());
//		email.setText(selectedPoduct.isActive());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		selectedPoduct.setName(name.getText());
//		selectedPoduct.setAddress(address.getText());
//		selectedPoduct.setPhone(phone.getText());
//		selectedPoduct.setEmail(email.getText());
		accessControlManager.createOrUpdateProduct(selectedPoduct);
		
		close();
	}

}
