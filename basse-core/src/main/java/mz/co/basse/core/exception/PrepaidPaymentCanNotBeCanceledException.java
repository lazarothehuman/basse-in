package mz.co.basse.core.exception;

import mz.co.basse.finance.core.model.Payment;

public class PrepaidPaymentCanNotBeCanceledException extends RuntimeException {

	private static final long serialVersionUID = -5123074715352103795L;

	public PrepaidPaymentCanNotBeCanceledException(Payment payment) {
		super("Pagamentos não podem ser cancelados: " + payment.getId());
	}

}
