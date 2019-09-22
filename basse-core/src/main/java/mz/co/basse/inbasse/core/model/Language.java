package mz.co.basse.inbasse.core.model;

import java.util.Locale;


public enum Language implements Internationalized {
	PORTUGUESE {
		@Override
		public Locale getLocale() {
			return PORTUGUESE_LOCALE;
		}

		public String getMessageKey() {
			return "portuguese";
		}
	},
	ENGLISH {
		@Override
		public Locale getLocale() {
			return Locale.ENGLISH;
		}

		public String getMessageKey() {
			return "english";
		}
	};

	private static final Locale PORTUGUESE_LOCALE = new Locale("pt");

	/**
	 * Deve retornar o Locale para a lingua que representa
	 * 
	 * @return
	 */
	abstract public Locale getLocale();

}
