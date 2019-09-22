package mz.co.basse.inbasse.accesscontrol.web.users;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.web.util.AutoClosableController;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChangePasswordController extends AutoClosableController {

	private static final long serialVersionUID = 4907966694471658256L;

	@Wire
	private Listbox usersList;

	@Wire
	private Window win;

	@Wire
	private Checkbox showPassword;

	@Wire
	private Textbox newPassword;

	@Wire
	private Textbox passwordConfirm;

	@Wire
	private Label lbuser;

	@Wire
	private Label lbname;

	@Wire
	private Label lbprofile;

	@WireVariable
	private AccessControlManager accessControlManager;

	private User user;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		user = (User) Sessions.getCurrent().getAttribute("User");
		lbuser.setValue(user.getLogin());
		lbname.setValue(user.getName());
		lbprofile.setValue(user.getProfile().getName());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		if (passwordConfirm.getText().equals(newPassword.getText())) {
			user.setPassword(newPassword.getText());
			accessControlManager.encryptUserPassword(user);
			accessControlManager.createOrUpdateUser(user);
			close();
		} else {
			throw new WrongValueException(
					Labels.getLabel("diferent.passwordConfirm"));
		}
	}

	@Listen("onCheck  = #showPassword")
	public void check() {
		if (showPassword.isChecked()) {
			newPassword.setType("text");
			passwordConfirm.setType("text");
		} else {
			newPassword.setType("password");
			passwordConfirm.setType("password");
		}
	}
}
