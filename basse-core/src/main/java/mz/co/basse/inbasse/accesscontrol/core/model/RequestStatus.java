package mz.co.basse.inbasse.accesscontrol.core.model;

import mz.co.basse.inbasse.core.model.Internationalized;

public enum RequestStatus implements Internationalized {
	FINISHED {

		public String getMessageKey() {
			return "finished.record";
		}
	},
	ONGOING {

		public String getMessageKey() {
			return "ongoing.record";
		}
	},
	PENDENT {

		public String getMessageKey() {
			return "pendent";
		}
	};

}
