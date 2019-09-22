package mz.co.basse.inbasse.finance.core.model;

import mz.co.basse.inbasse.core.model.Internationalized;

public enum FormOfPayment implements Internationalized {
	CASH {
		public String getMessageKey() {
			return "cash";
		}
		
		public String getDetailedMessageKey() {
			return getMessageKey();
		}
		
	},
	CARD {
		public String getMessageKey() {
			return "card";
		}
		
		public String getDetailedMessageKey() {
			return getMessageKey();
		}
	},
	DEPOSIT {
		public String getMessageKey() {
			return "deposit";
		}
		
		public String getDetailedMessageKey() {
			return getMessageKey();
		}
	},
	BANK_TRANSFER {
		public String getMessageKey() {
			return "bank.transfer";
		}

		public String getDetailedMessageKey() {
			// TODO Auto-generated method stub
			return getMessageKey();
		}
	},
	CHEQUE {
		public String getMessageKey() {
			return "cheque";
		}
		
		public String getDetailedMessageKey() {
			// TODO Auto-generated method stub
			return getMessageKey();
		}
	};
	
}
