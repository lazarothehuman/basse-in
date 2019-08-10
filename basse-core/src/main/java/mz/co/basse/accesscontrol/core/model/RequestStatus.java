package mz.co.basse.accesscontrol.core.model;

import mz.co.basse.core.model.Internationalized;

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
