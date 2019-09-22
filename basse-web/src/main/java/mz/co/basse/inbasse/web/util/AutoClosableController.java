package mz.co.basse.inbasse.web.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;

public abstract class AutoClosableController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 3596275369739954737L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		SessionHelper.getMainController().bindUbindedTabToController(this);
	}
	
	protected void close() {
		SessionHelper.getMainController().closeControllersTab(this);
	}
}
