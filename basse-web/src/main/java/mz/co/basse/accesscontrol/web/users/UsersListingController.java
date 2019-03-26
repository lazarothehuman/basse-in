package mz.co.basse.accesscontrol.web.users;

import java.util.List;

import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;
import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.User;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsersListingController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -8095035653522118249L;

	@Wire
	private Listbox usersList;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Checkbox showInactives;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = #searchButton")
	public void updateGrid() {
		UIHelper.clearListbox(usersList);
		List<User> users = accessControlManager.findUsers(!showInactives
				.isChecked());
		for (User user : users) {
			Listitem listitem = new Listitem();
			listitem.setValue(user);
			listitem.appendChild(new Listcell(user.getName()));
			listitem.appendChild(new Listcell(user.getLogin()));
			listitem.appendChild(new Listcell(user.getProfile().getName()));
			listitem.appendChild(new Listcell(Labels.getLabel(user.getLanguage().getMessageKey())));
			listitem.appendChild(new Listcell(FormatUtils.formatBoolean(user
					.isActive())));
			usersList.appendChild(listitem);
		}
		UIHelper.setRecordsNumber(users.size(), usersList);
	}

	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController()
				.createNewTab(Labels.getLabel("add.user"),
						"accesscontrol/users/add-user.zul");
	}

	@Listen("onDoubleClick = #usersList")
	public void modify() {
		User selectedUser = usersList.getSelectedItem().getValue();
		SessionHelper.setObject("selectedUser", selectedUser);
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("modify.user"),
				"accesscontrol/users/modify-user.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		if (usersList.getSelectedItem() != null) {
			Messagebox.show(Labels.getLabel("inactivate.user.question"),
					Labels.getLabel("inactivate.user.question"), Messagebox.OK
							| Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {
						public void onEvent(Event e) {
							if (Messagebox.ON_OK.equals(e.getName())) {
								User user = usersList.getSelectedItem()
										.getValue();
								user.setActive(!user.isActive());
								accessControlManager.createOrUpdateUser(user);
								updateGrid();
							}
						}
					});
		} else {
			throw new WrongValueException(Labels.getLabel("select.user"));
		}
	}

	@Listen("onClick = #changePassword")
	public void changePassword() {		
		if (usersList.getSelectedItem() != null) {			
			SessionHelper.getMainController().createNewTab(
					Labels.getLabel("change.password"),
					"accesscontrol/users/change-Password.zul");

			User user2 = usersList.getSelectedItem().getValue();
			Sessions.getCurrent().setAttribute("User", user2);
			
		} else {
			throw new WrongValueException(Labels.getLabel("select.user"));
		}
	}
}
