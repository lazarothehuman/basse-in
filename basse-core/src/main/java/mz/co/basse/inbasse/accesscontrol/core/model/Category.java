package mz.co.basse.inbasse.accesscontrol.core.model;

import mz.co.basse.inbasse.core.model.Internationalized;

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
