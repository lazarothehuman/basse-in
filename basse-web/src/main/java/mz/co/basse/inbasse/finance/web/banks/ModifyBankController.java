package mz.co.basse.inbasse.finance.web.banks;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import mz.co.basse.inbasse.core.exception.DuplicateEntryException;
import mz.co.basse.inbasse.finance.core.manager.FinanceManager;
import mz.co.basse.inbasse.finance.core.model.Bank;
import mz.co.basse.inbasse.web.util.AutoClosableController;
import mz.co.basse.inbasse.web.util.SessionHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ModifyBankController extends AutoClosableController {

	private static final long serialVersionUID = -3408717958305588442L;

	@Wire
	private Textbox name;

	@WireVariable
	private FinanceManager financeManager;

	private Bank selectedBank;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		selectedBank = (Bank) SessionHelper.takeObject("selectedBank");
		name.setText(selectedBank.getName());
	}

	@Listen("onClick = #saveButton")
	public void save() {
		selectedBank.setName(name.getText());
		try {
			financeManager.updateBank(selectedBank);
		} catch (DuplicateEntryException e) {
			throw new WrongValueException(
					Labels.getLabel("bank.already.exists"));
		}

		close();
	}
}
