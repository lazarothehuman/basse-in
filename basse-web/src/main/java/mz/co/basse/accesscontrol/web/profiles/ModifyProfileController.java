package mz.co.basse.accesscontrol.web.profiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.Transaction;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModifyProfileController extends AutoClosableController {

	private static final long serialVersionUID = -3408717958305588442L;

	@Wire
	private Textbox name;

	@WireVariable
	private AccessControlManager accessControlManager;

	private Profile profile;

	@Wire
	private Listbox transactionsList;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		profile = (Profile) SessionHelper.takeObject("selectedProfile");
		name.setText(profile.getName());

		List<Transaction> transactions = accessControlManager
				.findTransactions();
		Set<Listitem> selectedTransactions = new HashSet<Listitem>();
		for (Transaction transaction : transactions) {
			Listitem listitem = new Listitem();
			listitem.setValue(transaction);
			listitem.appendChild(new Listcell(Labels.getLabel(transaction
					.getName())));
			if (profile.getTransactions().contains(transaction)) {
				selectedTransactions.add(listitem);
			}
			transactionsList.appendChild(listitem);
		}
		transactionsList.setSelectedItems(selectedTransactions);
	}

	@Listen("onClick = #saveButton")
	public void save() {
		profile.getTransactions().clear();
		profile.setName(name.getText());
		Set<Listitem> selectedItems = transactionsList.getSelectedItems();
		if (selectedItems.isEmpty()) {
			throw new WrongValueException(
					Labels.getLabel("select.profile.transactions"));
		}
		profile.getTransactions().clear();
		for (Listitem listitem : selectedItems) {
			Transaction transaction = listitem.getValue();
			profile.getTransactions().add(transaction);
		}
		accessControlManager.createOrUpdateProfile(profile);
		close();
	}
}