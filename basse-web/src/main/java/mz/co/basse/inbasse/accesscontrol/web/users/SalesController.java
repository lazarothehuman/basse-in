package mz.co.basse.inbasse.accesscontrol.web.users;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.web.util.AutoClosableController;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SalesController extends AutoClosableController {

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

}
