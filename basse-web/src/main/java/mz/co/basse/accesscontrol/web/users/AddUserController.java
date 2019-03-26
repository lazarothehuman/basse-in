package mz.co.basse.accesscontrol.web.users;

import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.UIHelper;
import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.User;
import mz.co.basse.core.exception.DuplicateEntryException;
import mz.co.basse.core.model.Language;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddUserController extends AutoClosableController {

	private static final long serialVersionUID = -3408717958305588442L;

	@Wire
	private Textbox login;

	@Wire
	private Textbox name;

	@Wire
	private Combobox profile;

	@Wire
	private Combobox language;

	@Wire
	private Textbox password;

	@Wire
	private Textbox passwordConfirm;
	
	@Wire
	private Checkbox showPassword;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		UIHelper.buildProfileCombobox(accessControlManager.findProfiles(true),
				profile);
		UIHelper.buildLanguageCombobox(language);
	}

	@Listen("onClick = #saveButton")
	public void save() {
		try {
			if (password.getText().equals(passwordConfirm.getText())) {
				User user = new User();
				user.setLogin(login.getText());
				user.setName(name.getText());
				user.setLanguage((Language) language.getSelectedItem().getValue());
				Profile selectedProfile = profile.getSelectedItem().getValue();
				user.setProfile(selectedProfile);
				user.setPassword(password.getText());
				accessControlManager.encryptUserPassword(user);
				accessControlManager.createOrUpdateUser(user);
				
				
			} else {
				throw new WrongValueException(
						Labels.getLabel("diferent.passwordConfirm"));
			}
		} catch (DuplicateEntryException e) {
			throw new WrongValueException(
					Labels.getLabel("users.already.exists"));
		}
		close();
	}
	
	@Listen("onCheck  = #showPassword")
	public void check() {
		if (showPassword.isChecked()) {
			password.setType("text");
			passwordConfirm.setType("text");
		} else {
			password.setType("password");
			passwordConfirm.setType("password");
		}
	}
}
