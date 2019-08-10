package mz.co.basse.accesscontrol.core.model;

import mz.co.basse.core.model.Internationalized;

public enum Category implements Internationalized {
	
	IT {

		public String getMessageKey() {
			return "informatics.record";
		}
	},
	ALARMS {

		public String getMessageKey() {
			return "alarms.record";
		}
	};

}
