package mz.co.basse.accesscontrol.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import mz.co.basse.core.model.Activable;
import mz.co.basse.core.model.Identifiable;

//@Entity
//@Table(name = "service")
public class Service implements Identifiable, Activable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -971415687363233245L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public Long getId() {
		return id;
	}
}
