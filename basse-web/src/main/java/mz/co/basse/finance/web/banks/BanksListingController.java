package mz.co.basse.finance.web.banks;

import java.util.List;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import mz.co.basse.finance.core.manager.FinanceManager;
import mz.co.basse.finance.core.model.Bank;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

/**
 * 
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BanksListingController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -8095035653522118259L;

	@Wire
	private Listbox banksList;

	@Wire
	private Checkbox showInactives;

	@WireVariable
	private FinanceManager financeManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = #searchButton")
	public void updateGrid() {
		UIHelper.clearListbox(banksList);
		List<Bank> banks = financeManager.findBanks(!showInactives.isChecked());		
		ListModelList<Bank> banksModel = new ListModelList<Bank>(banks);
		banksList.setModel(banksModel);
	}

	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.bank"),
				"finance/banks/add-bank.zul");
	}

	@Listen("onDoubleClick = #banksList")
	public void modify() {
		Bank selectedBank = getSelectedBank();
		SessionHelper.setObject("selectedBank", selectedBank);
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("modify.bank"),
				"finance/banks/modify-bank.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		final Bank bank = getSelectedBank();
		Messagebox.show(Labels.getLabel("inactivate.bank.question"),
				Labels.getLabel("confirmation"),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							Bank loadedBank = financeManager
									.findBank(bank.getName());
							loadedBank.setActive(!loadedBank.isActive());
							financeManager.updateBank(loadedBank);
							updateGrid();
						}
					}
				});
	}

	private Bank getSelectedBank() {
		if (banksList.getSelectedItem() != null) {
			return banksList.getSelectedItem().getValue();
		} else {
			throw new WrongValueException(Labels.getLabel("select.bank"));
		}
	}

	public String formatBoolean(boolean value) {
		return FormatUtils.formatBoolean(value);
	}
}
