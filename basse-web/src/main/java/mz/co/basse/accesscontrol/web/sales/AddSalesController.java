package mz.co.basse.accesscontrol.web.sales;

import java.math.BigDecimal;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.time.DateUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.core.model.Internationalized;
import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddSalesController extends AutoClosableController {

	private static final long serialVersionUID = 6720145638212375L;

	@Wire
	private Label issueDate;

	@Wire
	private Combobox service;

	@Wire
	private Combobox product;

	@Wire
	private Decimalbox unitPrice;

	@Wire
	private Decimalbox itemQuantity;

	@Wire
	private Decimalbox total;

	@Wire
	private Textbox description;

	@Wire
	private Listbox itemsList;

	@Wire
	private Listfooter totalItems;

	@Wire
	private Listfooter totalAmount;
	
	@WireVariable
	private AccessControlManager accessControlManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<Product> products = accessControlManager.findProducts(null, null, true);
		UIHelper.buildProductsCombobox(products, product);
//		List<Service> services = new ArrayList<Service>();
//		UIHelper.buildServicesCombobox(services,service);
		Calendar calendar = Calendar.getInstance();
		issueDate.setValue(FormatUtils.getDateformatString());
	}

	@Listen("onClick = #saveButton")
	public void save() {

	}

	@Listen("onClick = #newItem")
	public void newItem() {
		clearItemFields();
		itemsList.clearSelection();
		updateItemsListFootersValue();
	}

	@Listen("onClick = #removeItem")
	public void removeItem() {

		Listitem selectedInvoiceItem = (Listitem) UIHelper
				.nullSafeGetSelectedItemValue(itemsList);
		if (selectedInvoiceItem == null) {
			throw new WrongValueException(
					Labels.getLabel("item.select"));
		}

		itemsList.removeItemAt(itemsList.getSelectedIndex());

		clearItemFields();
		updateItemsListFootersValue();

	}

	@Listen("onClick = #saveItem")
	public void saveItem() {

	}

	public void clearItemFields() {

		UIHelper.clearConstrainedField(description);
		UIHelper.clearConstrainedField(itemQuantity);
		UIHelper.clearConstrainedField(unitPrice);
		product.setSelectedIndex(-1);
		UIHelper.clearConstrainedField(total);
	}

	@Listen("onChange = #unitPrice ; onChange = #itemQuantity")
	public void updateTotal() {

		BigDecimal totalVar = BigDecimal.ZERO;
		if (unitPrice.getValue() != null)
			totalVar = totalVar.add(unitPrice.getValue());
		if (itemQuantity.getValue() != null)
			totalVar = totalVar.multiply(itemQuantity.getValue());

		total.setValue(FormatUtils.getDecimalFormat().format(totalVar));

	}

	public void updateItemsListFootersValue() {
		totalItems.setLabel(Labels.getLabel("items.count",
				new Object[] { itemsList.getItemCount() }));

//		List<InvoiceItem> invoiceItems = UIHelper
//				.nullSafeGetAllInvoiceItems(itemsList);
//		BigDecimal total = BigDecimal.ZERO;
//		for (InvoiceItem invoiceItem : invoiceItems) {
//			total = total.add(invoiceItem.getTotal());
//		}
//
//		totalAmount
//				.setLabel(Labels.getLabel(
//						"invoice.items.total",
//						new Object[] { FormatUtils.getCurrencyFormat().format(
//								total) }));
	}

	@Listen("onSelect = #invoiceItemList")
	public void loadSelectedInvoiceItemFromList() {

//		Product selectedProductItem = (Product) UIHelper
//				.nullSafeGetSelectedItemValue(itemList);
//		if (selectedProductItem != null) {
//			description.setValue(selectedProductItem.getDescription());
//			itemQuantity.setValue(selectedProductItem.getQuantity());
//			unitPrice.setValue(selectedProductItem.getUnitPrice());
//			total.setValue(selectedProductItem.getTotal());
//			UIHelper.setSelectedValueOnProductCombobox(product,
//					selectedProductItem);
//		}
	}

	@Listen("onClick = #cancelButton")
	public void cancel() {
		close();
	}
	
	public String internationalize(Internationalized internationalized) {
		return Labels.getLabel(internationalized.getMessageKey());
	}

}
