package mz.co.basse.accesscontrol.core.model;

public enum DocumentNumerationType {
	BAR_CODE(false), RECEIPT(true), TICKET_INVOICE(true), TICKET_NUMBER(
			true), CREDIT_NOTE(true), DEBIT_NOTE(true), INVOICE(true);

	private boolean anual;

	public boolean isAnual() {
		return anual;
	}

	private DocumentNumerationType(boolean anual) {
		this.anual = anual;
	}

}
