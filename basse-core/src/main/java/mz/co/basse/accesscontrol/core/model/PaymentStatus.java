package mz.co.basse.accesscontrol.core.model;

import mz.co.basse.core.model.Internationalized;

public enum PaymentStatus implements Internationalized {
	OPEN {
		public String getMessageKey() {
			return "payment.status.open";
		}
	},
	PAID {
		public String getMessageKey() {
			return "payment.status.paid";
		}
	},
	PARTIALLY_PAID {
		public String getMessageKey() {
			return "payment.status.partially.paid";
		}
	};
	
	public String getDetailedMessageKey() {
		return getMessageKey();
	}
}
