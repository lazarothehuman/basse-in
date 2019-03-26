package mz.co.basse.web.util;

import mz.co.basse.web.main.MainController;
import mz.co.basse.accesscontrol.core.model.User;

import org.zkoss.zk.ui.Sessions;

public class SessionHelper {
	public static User getUser() {
		return (User) Sessions.getCurrent().getAttribute("user");
	}

	public static void setUser(User user) {
		Sessions.getCurrent().setAttribute("user", user);
	}

	public static MainController getMainController() {
		return (MainController) Sessions.getCurrent().getAttribute("mainController");
	}

	public static void setMainController(MainController mainController) {
		Sessions.getCurrent().setAttribute("mainController", mainController);
	}

	public static void setObject(String key, Object object) {
		Sessions.getCurrent().setAttribute(key, object);
	}

	public static Object takeObject(String key) {
		Object object = (Object) Sessions.getCurrent().getAttribute(key);
		Sessions.getCurrent().setAttribute(key, null);
		return object;
	}
}
