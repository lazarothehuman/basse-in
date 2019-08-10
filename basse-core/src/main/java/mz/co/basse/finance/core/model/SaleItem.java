package mz.co.basse.finance.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mz.co.basse.accesscontrol.core.model.ProductPrice;

@Table(name = "sale_item")
@Entity
public class SaleItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Sale sale;
	
	@Column(nullable=false)
	private BigDecimal cost;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private ProductPrice product;
	
	@Column(columnDefinition = "bit", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public ProductPrice getProduct() {
		return product;
	}

	public void setProduct(ProductPrice product) {
		this.product = product;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
