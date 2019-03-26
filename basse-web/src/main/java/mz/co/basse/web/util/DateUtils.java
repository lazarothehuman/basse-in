package mz.co.basse.web.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return org.apache.commons.lang.time.DateUtils.truncate(
				calendar.getTime(), Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	public static Date getPastDate(int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -numberOfDays);
		return org.apache.commons.lang.time.DateUtils.truncate(
				calendar.getTime(), Calendar.DAY_OF_MONTH);
	}
}
