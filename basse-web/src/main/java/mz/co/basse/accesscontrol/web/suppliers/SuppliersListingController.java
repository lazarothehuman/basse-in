package mz.co.basse.accesscontrol.web.suppliers;

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
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Supplier;
import mz.co.basse.core.model.Internationalized;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SuppliersListingController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -3344423748395580078L;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Textbox name, email, phone;

	@Wire
	private Listbox suppliersList;

	private ListModelList<Supplier> suppliersModelList;

	@Wire
	private Checkbox showInactives;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = #searchButton")
	public void updateGrid() {
		UIHelper.clearListbox(suppliersList);
		String typedName = UIHelper.nullIfEmpty(name.getText());
		String typedAddress = UIHelper.nullIfEmpty(name.getText());
		String typedEmail = UIHelper.nullIfEmpty(email.getText());
		String typedPhone = UIHelper.nullIfEmpty(phone.getText());
		List<Supplier> suppliers = accessControlManager.findSuppliers(null, typedName, typedEmail, typedAddress,
				typedPhone, !showInactives.isChecked());
		if (suppliers != null) {
			suppliersModelList = new ListModelList<Supplier>(suppliers);
			suppliersList.setModel(suppliersModelList);
			UIHelper.setRecordsNumber(suppliersModelList.size(), suppliersList);
		} else {
			Messagebox.show(Labels.getLabel("empty.search"), Labels.getLabel("empty.search"), Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController().createNewTab(Labels.getLabel("add.supplier"),
				"/accesscontrol/suppliers/add-supplier.zul");
	}

	@Listen("onDoubleClick = #suppliersList")
	public void modify() {
		Supplier selectedSupplier = getSelectedSupplier();
		SessionHelper.setObject("selectedSupplier", selectedSupplier);
		SessionHelper.getMainController().createNewTab(Labels.getLabel("modify.supplier"),
				"/accesscontrol/suppliers/modify-supplier.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		final Supplier supplier = getSelectedSupplier();
		Messagebox.show(Labels.getLabel("inactivate.supplier.question"), Labels.getLabel("confirmation"),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							supplier.setActive(!supplier.isActive());
							accessControlManager.updateSupplier(supplier);
							updateGrid();
						}
					}
				});
	}

	public String internationalize(Internationalized internationalized) {
		return internationalized.getMessageKey();
	}

	public String formatBoolean(Boolean value) {
		return FormatUtils.formatBoolean(value);
	}

	private Supplier getSelectedSupplier() {
		if (suppliersList.getSelectedItem() != null) {
			return suppliersList.getSelectedItem().getValue();
		} else {
			throw new WrongValueException(Labels.getLabel("select.supplier"));
		}
	}
}