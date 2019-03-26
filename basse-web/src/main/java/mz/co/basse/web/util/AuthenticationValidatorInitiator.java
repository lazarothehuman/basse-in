package mz.co.basse.web.util;

import java.util.Map;

import mz.co.basse.accesscontrol.core.model.User;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class AuthenticationValidatorInitiator implements Initiator {

	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		User user = SessionHelper.getUser();
		if (user == null) {
			Executions.sendRedirect("accesscontrol/login.zul");
		}
	}

}
