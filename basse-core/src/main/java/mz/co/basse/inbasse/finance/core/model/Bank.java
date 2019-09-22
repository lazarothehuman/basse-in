package mz.co.basse.inbasse.finance.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import mz.co.basse.inbasse.core.model.Activable;
import mz.co.basse.inbasse.core.model.Identifiable;

@Entity
@Table(name = "bank")
public class Bank implements Identifiable, Activable {

	private static final long serialVersionUID = -1376363548905540005L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bank) {
			Bank other = (Bank) obj;
			return new EqualsBuilder().append(name, other.name).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}
}
