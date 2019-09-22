package mz.co.basse.inbasse.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculationUtils {
	private static MathContext mathContext = new MathContext(11,
			RoundingMode.HALF_UP);

	public static BigDecimal calculatePercentageValue(BigDecimal value,
			BigDecimal percentage) {
		BigDecimal fraction = percentage.divide(BigDecimal.valueOf(100),
				mathContext);
		return value.multiply(fraction, mathContext).setScale(2);
	}

}
