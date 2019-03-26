package mz.co.basse.accesscontrol.web.users;

import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.User;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
public class ChangePasswordUserController extends AutoClosableController {

	private static final long serialVersionUID = 4907966694471658256L;

	@Wire
	private Listbox usersList;

	@Wire
	private Window win;

	@Wire
	private Checkbox showPassword;

	@Wire
	private Textbox currentPassword;

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

		user = SessionHelper.getUser();
		lbuser.setValue(user.getLogin());
		lbname.setValue(user.getName());
		lbprofile.setValue(user.getProfile().getName());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		User loadedUser = accessControlManager.findUser(SessionHelper.getUser()
				.getLogin());
		if (accessControlManager.passwordMatches(currentPassword.getText(),
				loadedUser.getPassword())) {
			if (passwordConfirm.getText().equals(newPassword.getText())) {
				user.setPassword(newPassword.getText());
				accessControlManager.encryptUserPassword(user);
				accessControlManager.createOrUpdateUser(user);
				close();
			} else {
				throw new WrongValueException(
						Labels.getLabel("diferent.passwordConfirm"));
			}
		} else {
			throw new WrongValueException(currentPassword,
					Labels.getLabel("current.password.does.not.match"));
		}
	}

	@Listen("onCheck  = #showPassword")
	public void check() {
		if (showPassword.isChecked()) {
			currentPassword.setType("text");
			newPassword.setType("text");
			passwordConfirm.setType("text");
		} else {
			currentPassword.setType("password");
			newPassword.setType("password");
			passwordConfirm.setType("password");
		}
	}
}
