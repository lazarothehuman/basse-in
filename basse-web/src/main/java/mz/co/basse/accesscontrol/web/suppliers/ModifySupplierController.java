package mz.co.basse.accesscontrol.web.suppliers;


import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Supplier;
import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;



@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModifySupplierController extends AutoClosableController {

	private static final long serialVersionUID = -1144071812860395000L;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Textbox name, email, phone, address;
	
	private Supplier supplier;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		supplier = (Supplier) SessionHelper.takeObject("selectedSupplier");

	}

	@Listen("onClick = #saveButton")
	public void save() {
		String typedName = UIHelper.nullIfEmpty(name.getText());
		String typedEmail = UIHelper.nullIfEmpty(email.getText());
		String typedPhone = UIHelper.nullIfEmpty(phone.getText());
		String typedAddress= UIHelper.nullIfEmpty(address.getText());
		if (typedName != null) {
			supplier.setName(typedName);
			supplier.setEmail(typedEmail);
			supplier.setPhone(typedPhone);
			supplier.setAddress(typedAddress);
			accessControlManager.updateSupplier(supplier);
			close();
		} else {
			throw new WrongValueException(Labels.getLabel("fill.null.components"));
		}
	}

}