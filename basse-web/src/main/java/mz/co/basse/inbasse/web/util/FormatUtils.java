package mz.co.basse.inbasse.web.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Formatadores Padrão, utilizados nos sistemas.
 * 
 * 
 */
public class FormatUtils {

	
	private static final String[] months = {  "January", "February", "March",
	                             "April", "May","June",
	                             "July", "August","September", 
	                             "October", "November","December"};
	private static final DecimalFormat currencyFormat = new DecimalFormat(
			"###,###,###,###,##0.00");
	private static final DecimalFormat decimalFormat = new DecimalFormat(
			"#################0.####");
	private static final DecimalFormat percentFormat = new DecimalFormat(
			"###,###,###,###,##0.00'%'");

	private static final DateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static final DateFormat monthFormat = new SimpleDateFormat(
			"MM/yyyy");

	public static final DecimalFormat getCurrencyFormat() {
		return currencyFormat;
	}

	public static DecimalFormat getDecimalFormat() {
		return decimalFormat;
	}

	public static DateFormat getDateformat() {
		return dateFormat;
	}

	public static String getDateformatString() {
		return "dd/MM/yyyy";
	}

	public static DecimalFormat getPercentFormat() {
		return percentFormat;
	}

	public static String createPeriodDescription(Date startDate, Date endDate) {
		return dateFormat.format(startDate) + " - "
				+ dateFormat.format(endDate);
	}

	public static DateFormat getMonthFormat() {
		return monthFormat;
	}

	public static String formatBoolean(boolean value) {
		return value ? "Sim" : "Não";
	}

	public static String numberInWords(BigDecimal value) {
		return new JExtenso(value).toString();
	}
	
	public static String getMonthString( int i ){
		return months[i];
	}
	
	public static int getMonthNumber ( Date date)
	{
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
         
        return cal.get(Calendar.MONTH);

	}
}
