package mz.co.basse.inbasse.accesscontrol.web.users;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.Profile;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.core.model.Language;
import mz.co.basse.inbasse.web.util.AutoClosableController;
import mz.co.basse.inbasse.web.util.SessionHelper;
import mz.co.basse.inbasse.web.util.UIHelper;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModifyUserController extends AutoClosableController {

	private static final long serialVersionUID = -3408717958305588442L;

	@Wire
	private Label login;

	@Wire
	private Textbox name;

	@Wire
	private Combobox profile;

	@Wire
	private Combobox language;

	@Wire
	private Checkbox showPassword;

	@WireVariable
	private AccessControlManager accessControlManager;

	private User user;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (User) SessionHelper.takeObject("selectedUser");
		UIHelper.buildProfileCombobox(accessControlManager.findProfiles(true),
				profile);
		UIHelper.buildLanguageCombobox(language);
		login.setValue(user.getLogin());
		name.setText(user.getName());

		UIHelper.setSelectedValueOnCombobox(language, user.getLanguage());
		UIHelper.setSelectedValueOnCombobox(profile, user.getProfile());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		user.setName(name.getText());
		Profile selectedProfile = profile.getSelectedItem().getValue();
		user.setProfile(selectedProfile);
		Language selectLanguage = language.getSelectedItem().getValue();
		user.setLanguage(selectLanguage);
		accessControlManager.createOrUpdateUser(user);
		close();
	}
}
