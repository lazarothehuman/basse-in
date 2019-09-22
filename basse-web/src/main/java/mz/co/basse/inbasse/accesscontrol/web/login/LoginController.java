package mz.co.basse.inbasse.accesscontrol.web.login;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.web.util.SessionHelper;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 5330522267912172002L;

	@Wire
	private Textbox user;

	@Wire
	private Textbox password;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Listen("onClick = #loginButton; onOK = #user,#password")
	public void login() {
		User applicationUser = accessControlManager.findUser(user.getText());
		boolean loginOK = false;
		if (applicationUser != null) {
			loginOK = accessControlManager.passwordMatches(password.getText(),
					applicationUser.getPassword());
		}
		if (!loginOK) {
			Messagebox.show("Usuário Inexistente ou senha incorrecta",
					"Erro de Login", Messagebox.OK, Messagebox.ERROR);
		} else {
			SessionHelper.setUser(applicationUser);
			SessionHelper.setObject("org.zkoss.web.preferred.locale",
					applicationUser.getLanguage().getLocale());
			Executions.sendRedirect("/main.zul");
		}
	}

}
