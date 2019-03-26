package mz.co.basse.web.util;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Product;
import mz.co.basse.accesscontrol.core.model.Profile;
import mz.co.basse.accesscontrol.core.model.Transaction;
import mz.co.basse.core.model.Identifiable;
import mz.co.basse.core.model.Language;
import mz.co.basse.finance.core.model.Bank;
import mz.co.basse.finance.core.model.FormOfPayment;

import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

public class UIHelper {
	public static void clearListbox(Listbox listbox) {
		List<Component> children = listbox.getChildren();
		for (Iterator<Component> iterator = children.iterator(); iterator
				.hasNext();) {
			Component component = iterator.next();
			if (component instanceof Listitem || component instanceof Listfoot) {
				iterator.remove();
			}
		}
	}

	public static void clearGrid(Grid grid) {
		Rows rows = grid.getRows();
		List<Component> children = rows.getChildren();
		for (Iterator<Component> iterator = children.iterator(); iterator
				.hasNext();) {
			iterator.next();
			iterator.remove();
		}
	}

	public static String createPeriodDescrition(Date startDate, Date endDate) {
		return FormatUtils.getDateformat().format(startDate) + " - "
				+ FormatUtils.getDateformat().format(endDate);
	}

	public static void clearCombobox(Combobox combobox) {
		List<Component> children = combobox.getChildren();
		for (Iterator<Component> iterator = children.iterator(); iterator
				.hasNext();) {
			iterator.next();
			iterator.remove();
		}
	}
	
	public static String nullIfEmpty(String value) {
		if (value.isEmpty()) {
			return null;
		}
		return value;
	}

	public static void setSelectedValueOnCombobox(Combobox combobox,
			Object value) {
		List<Component> children = combobox.getChildren();
		for (Component component : children) {
			Comboitem comboitem = (Comboitem) component;
			if (comboitem.getValue().equals(value)) {
				combobox.setSelectedItem(comboitem);
				return;
			}
		}
	}

	public static void buildLanguageCombobox(Combobox combobox) {
		Comboitem portuguese = new Comboitem("Português");
		portuguese.setValue(Language.PORTUGUESE);
		Comboitem english = new Comboitem("Inglês");
		english.setValue(Language.ENGLISH);
		combobox.appendChild(portuguese);
		combobox.appendChild(english);
	}

	public static Object nullSafeGetSelectedItemValue(Combobox combobox) {
		if (combobox != null) {
			Comboitem selectedItem = combobox.getSelectedItem();
			if (selectedItem != null) {
				return selectedItem.getValue();
			}
		}
		return null;
	}
	
	public static Identifiable nullSafeGetSelectedItemValue(Listbox listbox) {
		if (listbox != null) {
			Listitem selectedItem = listbox.getSelectedItem();
			if (selectedItem != null) {
				return selectedItem.getValue();
			}
		}
		return null;
	}

	public static Identifiable nullSafeGetSelectedItemValueOnListbox(Listbox listbox) {
		if (listbox != null) {
			Listitem selectedItem = listbox.getSelectedItem();
			if (selectedItem != null) {
				return selectedItem.getValue();
			}
		}
		return null;
	}

	public static <T> List<T> nullSafeGetSelectedItemValuesOnListbox(Listbox listbox, T clazz) {
		if (listbox != null) {
			Set<Listitem> selectedItems = listbox.getSelectedItems();
			if (selectedItems.size() > 0) {
				ArrayList<T> selectedValues = new ArrayList<T>();
				for (Listitem listitem : selectedItems) {
					selectedValues.add(listitem.<T>getValue());
				}
				return selectedValues;
			}
		}
		return null;
	}
	
	public static Window buildActionDetailWindow() {
		Window accountsListingWindow = (Window) Executions.createComponents(
				"visit/action.zul", null, null);
		accountsListingWindow.setWidth("90%");
		accountsListingWindow.setHeight("90%");
		accountsListingWindow.setTitle(Labels.getLabel("action.to.perform"));
		accountsListingWindow.setClosable(true);
		return accountsListingWindow;
	}

	public static void setSelectedValuesOnLIstBox(Listbox listbox,
			List<?> selectedObjects) {
		List<Listitem> items = listbox.getItems();
		Set<Listitem> selectedItems = new HashSet<Listitem>();
		for (Listitem listitem : items) {
			if (selectedObjects.contains(listitem.getValue())) {
				selectedItems.add(listitem);
			}
		}
		listbox.setSelectedItems(selectedItems);
	}

	public static void buildProfileCombobox(List<Profile> profiles,
			Combobox combobox) {
		for (Profile profile : profiles) {
			Comboitem comboitem = new Comboitem(profile.getName());
			comboitem.setValue(profile);
			combobox.appendChild(comboitem);
		}
	}

	public static Listfoot setRecordsNumber(int recordsNumber, Listbox listbox) {
		return setRecordsNumber(recordsNumber, listbox, 1);
	}

	public static Listfoot setRecordsNumber(int recordsNumber, Listbox listbox,
			int span) {
		Listfoot listfoot = new Listfoot();
		Listfooter listfooter = new Listfooter();
		listfooter.setSpan(span);
		listfooter.setLabel(Labels.getLabel("records.number") + ": "
				+ recordsNumber);
		listfoot.appendChild(listfooter);
		listbox.appendChild(listfoot);
		return listfoot;
	}

	public static void generateReport(Report report, String fileName) {
		Media media = new AMedia(fileName, "pdf", "application/pdf",
				report.getStream());
		SessionHelper.setObject("reportToGenerate", media);
		SessionHelper.getMainController().createNewTab(report.getName(),
				"reports/report.zul");
	}

	public static void clearConstrainedField(InputElement inputBox) {
		Constraint constraint = inputBox.getConstraint();
		inputBox.setConstraint("");
		inputBox.setText(null);
		inputBox.setConstraint(constraint);
	}
	
	public static void buildFormOfPaymentCombobox(Combobox combobox) {
		for (FormOfPayment formOfPayment : FormOfPayment.values()) {
			Comboitem comboItem = new Comboitem(
					Labels.getLabel(formOfPayment.getMessageKey()));
			comboItem.setValue(formOfPayment);
			combobox.appendChild(comboItem);
		}
	}
	
	public static void buildBankCombobox(List<Bank> banks, Combobox combobox) {
		for (Bank bank : banks) {
			Comboitem comboitem = new Comboitem(bank.getName());
			comboitem.setValue(bank);
			combobox.appendChild(comboitem);
		}
	}
	
	public static void openClientWindowForSelection(
			AccessControlManager accessControlManager, boolean contractual,
			String selectionQueue) {
		SessionHelper.setObject("contractual", contractual);
		SessionHelper.setObject("selection", true);
		SessionHelper.setObject("selectionQueue", selectionQueue);
		SessionHelper.getMainController().createNewTab(accessControlManager
				.findTransaction(Transaction.CLIENT_TRANSACTION_CODE));
	}

	public static void openClientWindowForSelection(
			AccessControlManager accessControlManager, boolean contractual) {
		openClientWindowForSelection(accessControlManager, contractual, null);
	}

	public static void buildProductsCombobox(List<Product> productsList, Combobox product) {
		for (Product prod : productsList) {
			Comboitem comboitem = new Comboitem(prod.getName());
			comboitem.setValue(prod);
			product.appendChild(comboitem);
		}		
	}

	public static void buildServicesCombobox(List<Service> servicesList, Combobox service) {
//		for (Service serv : servicesList) {
//			Comboitem comboitem = new Comboitem(serv.getName());
//			comboitem.setValue(serv);
//			service.appendChild(comboitem);
//		}		
	}
	
}