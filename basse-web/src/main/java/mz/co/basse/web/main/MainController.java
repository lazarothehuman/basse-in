package mz.co.basse.web.main;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.basse.web.util.SessionHelper;
import mz.co.basse.accesscontrol.core.manager.AccessControlManager;
import mz.co.basse.accesscontrol.core.model.Transaction;
import mz.co.basse.accesscontrol.core.model.User;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	private Tabbox contentTabbox;

	@Wire
	private Menu recordMenu;

	@Wire
	private Menu recordSystemMenu;

	@Wire
	private Menu reportsMenu;

	@Wire
	private Menu saleMenu;

	@Wire
	private Menu expenseMenu;
	
	@Wire
	private Menu financeMenu;

	@WireVariable
	private AccessControlManager accessControlManager;

	@Wire
	private Menuitem logoutMenuItem;

	@Wire
	private Label login;

	@Wire
	private Label profile;

	@Wire
	private Label clientOrAgent;

	private Tab unbindedTab;

	private Map<SelectorComposer<Component>, Tab> controllerToTab = new HashMap<SelectorComposer<Component>, Tab>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		User user = SessionHelper.getUser();
		login.setValue(Labels.getLabel("login.header",
				new Object[] { user.getLogin() }));
		profile.setValue(Labels.getLabel("profile.header", new Object[] { user
				.getProfile().getName() }));

		Map<String, MenuUsage> startCodeToMenu = new HashMap<String, MenuUsage>();
		// This one must allways be considered used because we hardcode the
		// logout menu item on the zul file
		startCodeToMenu.put("1", new MenuUsage(true, recordSystemMenu));
		startCodeToMenu.put("2", new MenuUsage(recordMenu));
		startCodeToMenu.put("3", new MenuUsage(saleMenu));
		startCodeToMenu.put("4", new MenuUsage(financeMenu));
		startCodeToMenu.put("5", new MenuUsage(reportsMenu));

		List<Transaction> transactions = user.getProfile().getTransactions();
		transactions = sort(transactions, on(Transaction.class).getCode());
		for (final Transaction transaction : transactions) {
			if (transaction.getUrl() != null) {
				String startCode = transaction.getCode().substring(0, 1);
				MenuUsage menuUsage = startCodeToMenu.get(startCode);
				Menu menu = menuUsage.getMenu();
				final String itemName = Labels.getLabel(transaction.getName());
				Menuitem menuitem = new Menuitem(itemName);
				menuitem.addEventListener("onClick",
						new EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								createNewTab(itemName, transaction.getUrl());
							}
						});
				menu.getMenupopup().appendChild(menuitem);
				menuUsage.setUsed(true);
			}
		}
		Collection<MenuUsage> menuUsages = startCodeToMenu.values();
		for (MenuUsage menuUsage : menuUsages) {
			if (!menuUsage.isUsed()) {
				menuUsage.getMenu().detach();
			}
		}
		SessionHelper.setMainController(this);
	}
	
	public void createNewTab(Transaction transaction) {
		createNewTab(Labels.getLabel(transaction.getName()),
				transaction.getUrl());
	}
	
	public void createNewTab(String name, String url) {
		Tab tab = new Tab(name);
		tab.setClosable(true);
		Include include = new Include();
		include.setSrc(url);
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.appendChild(include);
		contentTabbox.getTabs().appendChild(tab);
		contentTabbox.getTabpanels().appendChild(tabpanel);
		tab.setSelected(true);
		unbindedTab = tab;
	}

	public void bindUbindedTabToController(
			SelectorComposer<Component> controller) {
		controllerToTab.put(controller, unbindedTab);
		unbindedTab = null;
	}

	public void closeControllersTab(SelectorComposer<Component> controller) {
		controllerToTab.get(controller).close();
		controllerToTab.remove(controller);
	}

	@Listen("onClick = #logoutMenuItem")
	public void logout() {
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("accesscontrol/login.zul");
	}

}
