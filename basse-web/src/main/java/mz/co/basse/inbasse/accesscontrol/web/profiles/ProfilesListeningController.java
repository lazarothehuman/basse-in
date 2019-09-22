package mz.co.basse.inbasse.accesscontrol.web.profiles;

import java.util.List;

import mz.co.basse.inbasse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.inbasse.accesscontrol.core.model.Profile;
import mz.co.basse.inbasse.web.util.FormatUtils;
import mz.co.basse.inbasse.web.util.SessionHelper;
import mz.co.basse.inbasse.web.util.UIHelper;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
public class ProfilesListeningController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -3344423748395580078L;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Listbox profilesList;

	@Wire
	private Checkbox showInactives;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = #searchButton")
	public void updateGrid() {
		UIHelper.clearListbox(profilesList);
		List<Profile> profiles = accessControlManager
				.findProfiles(!showInactives.isChecked());
		for (Profile profile : profiles) {
			Listitem listitem = new Listitem();
			listitem.setValue(profile);
			listitem.appendChild(new Listcell(profile.getName()));
			listitem.appendChild(new Listcell(FormatUtils.formatBoolean(profile
					.isActive())));
			profilesList.appendChild(listitem);
		}
		UIHelper.setRecordsNumber(profiles.size(), profilesList);
	}

	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.profile"),
				"accesscontrol/profiles/add-profile.zul");
	}

	@Listen("onDoubleClick = #profilesList")
	public void modify() {
		Profile selectedProfile = profilesList.getSelectedItem().getValue();
		SessionHelper.setObject("selectedProfile", selectedProfile);
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("modify.profile"),
				"accesscontrol/profiles/modify-profile.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		if (profilesList.getSelectedItem() != null) {
			Messagebox.show(Labels.getLabel("inactivate.profile.question"),
					Labels.getLabel("inactivate.profile.question"),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {
						public void onEvent(Event e) {
							if (Messagebox.ON_OK.equals(e.getName())) {
								Profile profile = profilesList
										.getSelectedItem().getValue();
								profile.setActive(!profile.isActive());
								accessControlManager.updateProfile(profile);
								updateGrid();
							}
						}
					});
		} else {
			throw new WrongValueException(Labels.getLabel("select.profile"));
		}
	}
}