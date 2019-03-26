package mz.co.basse.accesscontrol.core.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mz.co.basse.core.model.Activable;
import mz.co.basse.core.model.Identifiable;

@Entity
@Table(name = "product")
public class Product implements Identifiable, Activable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 134482910905830659L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY	)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private BigDecimal pricePerUnit;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean limited;
	
	@OneToOne(mappedBy = "product")
	private Stock stock;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public boolean isLimited() {
		return limited;
	}

	public void setLimited(boolean limited) {
		this.limited = limited;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public Long getId() {
		return id;
	}

}
