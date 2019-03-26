package mz.co.basse.accesscontrol.core.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "document_numeration")
public class DocumentNumeration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DocumentNumerationType type;

	@Column(nullable = false)
	private long sequence;

	// quando nao for anual preenchemos ano com zero para a restrição unique
	// funcionar mesmo quando nao for um document numeration anual
	@Column(nullable = false)
	private int year;

	@Version
	@Column(nullable = false)
	private long version;

	DocumentNumeration() {
	}

	public DocumentNumeration(DocumentNumerationType type) {
		this.type = type;
		if (type.isAnual()) {
			Calendar calendar = Calendar.getInstance();
			this.year = calendar.get(Calendar.YEAR);
		}
	}

	public String next() {
		String number = String.valueOf(++sequence);
		if (type.isAnual()) {
			number += "/" + year;
		}
		return number;
	}

	public DocumentNumerationType getType() {
		return type;
	}

}
