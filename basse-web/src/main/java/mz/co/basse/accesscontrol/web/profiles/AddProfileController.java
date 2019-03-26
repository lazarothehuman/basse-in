package mz.co.basse.accesscontrol.web.profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mz.co.basse.web.util.AutoClosableController;
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
public class AddProfileController extends AutoClosableController {

	private static final long serialVersionUID = -1144071812860395000L;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Textbox name;

	@Wire
	private Listbox transactionsList;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		updateGrid();
	}

	public void updateGrid() {

		List<Transaction> transactions = accessControlManager
				.findTransactions();

		for (Transaction transaction : transactions) {
			Listitem listitem = new Listitem();
			listitem.setValue(transaction);
			listitem.appendChild(new Listcell(Labels.getLabel(transaction.getName())));
			transactionsList.appendChild(listitem);
		}
	}

	@Listen("onClick = #saveButton")
	public void save() {

		List<Transaction> transactions = new ArrayList<Transaction>();

		Profile profile = new Profile();
		profile.setName(name.getText());
		Set<Listitem> selectedItems = transactionsList.getSelectedItems();
		if (selectedItems.isEmpty()) {
			throw new WrongValueException(Labels.getLabel("select.profile.transactions"));
		}
		for (Listitem listitem : selectedItems) {
			Transaction transaction = listitem.getValue();
			transactions.add(transaction);
		}
		profile.setTransactions(transactions);
		accessControlManager.createOrUpdateProfile(profile);
		close();
	}
}