package mz.co.basse.accesscontrol.web.products;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.web.util.AutoClosableController;
import mz.co.basse.web.util.FormatUtils;
import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.web.util.UIHelper;

@VariableResolver(DelegatingVariableResolver.class)
public class ProductsListingController extends AutoClosableController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7085099167026997318L;

	private static final int RESULTS_LIMIT = 100;

	@Wire
	private Listbox productsList;

	@Wire
	private Textbox name;

	@Wire
	private Textbox nuit;

	@Wire
	private Textbox phone;

	@Wire
	private Listheader passengerColumn;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Checkbox contractual, showInactives;

	@Wire
	private Button selectButton;

	private String selectionQueue;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		selectButton.setVisible(false);
	}

	@Listen("onClick = #searchButton")
	public void search() {
		updateGrid(null);
	}
	public void updateGrid(String typedName) {
	//	UIHelper.clearListbox(productsList);
//		List<Product> products = null;
		//List<Product> products = accessControlManager.findProducts(name.getText(), null, !showInactives.isChecked());
		//if (products.size() == RESULTS_LIMIT) {
			//Messagebox.show(Labels.getLabel("search.was.limited"));
		//}
//		for (Product product : products) {
//			Listitem listitem = new Listitem();
//			listitem.setValue(product);
//			listitem.appendChild(new Listcell(product.getName()));
//			listitem.appendChild(new Listcell("Categoria"));
//			listitem.appendChild(new Listcell(FormatUtils.getCurrencyFormat().format(product.getPricePerUnit())));
//			listitem.appendChild(new Listcell(FormatUtils.formatBoolean(product.isLimited())));
//			listitem.appendChild(new Listcell(product.getStock().getQuantity()+""));
//			listitem.appendChild(
//					new Listcell(FormatUtils.formatBoolean(product.isActive())));
//			productsList.appendChild(listitem);
//		}
//		UIHelper.setRecordsNumber(products.size(), productsList);
//	}
	}
	@Listen("onClick = #addButton")
	public void add() {
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.product"),
				"accesscontrol/products/add-product.zul");
	}

	@Listen("onDoubleClick = #productsList")
	public void modify() {
		Product selectedProduct = getSelectedProduct();
		SessionHelper.setObject("selectedProduct", selectedProduct);
		SessionHelper.getMainController().createNewTab(
				Labels.getLabel("modify.product"),
				"accesscontrol/products/modify-product.zul");
	}

	@Listen("onClick = #inactivateButton")
	public void inactivate() {
		final Product product = getSelectedProduct();
		Messagebox.show(Labels.getLabel("inactivate.product.question"),
				Labels.getLabel("confirmation"),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					public void onEvent(Event e) {
						if (Messagebox.ON_OK.equals(e.getName())) {
							product.setActive(!product.isActive());
						//	accessControlManager.createOrUpdateProduct(product);
							updateGrid(null);
						}
					}
				});
	}

	private Product getSelectedProduct() {
		Listitem selectedItem = productsList.getSelectedItem();
		if (selectedItem != null) {
			return selectedItem.getValue();
		} else {
			throw new WrongValueException(Labels.getLabel("select.product"));
		}
	}


//	@Listen("onClick = #selectButton")
//	public void select() {
//		Product selectedProduct = getSelectedProduct();
//		String queueName = Queues.PRODUCT_SELECTION;
//		if (selectionQueue != null) {
//			queueName = selectionQueue;
//		}
//		EventQueue<Event> productSelectionQueue = EventQueues.lookup(queueName,
//				EventQueues.SESSION, true);
//		productSelectionQueue
//				.publish(new Event("productSelection", null, selectedProduct));
//		close();
//	}

}