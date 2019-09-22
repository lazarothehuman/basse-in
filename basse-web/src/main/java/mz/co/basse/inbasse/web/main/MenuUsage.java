package mz.co.basse.inbasse.web.main;

import org.zkoss.zul.Menu;

class MenuUsage {
	private Menu menu;
	private boolean used;

	public MenuUsage(Menu menu) {
		this(false, menu);
	}

	public MenuUsage(boolean used, Menu recordSystemMenu) {
		this.used = used;
		menu = recordSystemMenu;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}

}
