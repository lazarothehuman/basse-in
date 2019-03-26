package mz.co.basse.accesscontrol.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mz.co.basse.core.model.Identifiable;

@Entity
@Table(name = "product_sale_item")
public class ProductSaleItem implements Identifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385264806560136418L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "quantity_sold", nullable = false)
	private Long quantitySold;
	
	@Column(name = "price_unit", nullable = false)
	private BigDecimal priceUnit;
	
	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false) 
	private Sale sale;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;	
	
	
	public Long getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(Long quantitySold) {
		this.quantitySold = quantitySold;
	}

	public BigDecimal getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(BigDecimal priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public Long getId() {
		return id;
	}

}
