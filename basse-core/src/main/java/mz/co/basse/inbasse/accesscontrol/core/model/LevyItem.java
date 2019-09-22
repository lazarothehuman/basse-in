package mz.co.basse.inbasse.accesscontrol.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "levy_item")
public class LevyItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="levy_id")
	private Levy levy;
	
	@Column(nullable=false)
	private int quantity;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="product_price_id")
	private ProductPrice product;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Levy getLevy() {
		return levy;
	}

	public void setLevy(Levy levy) {
		this.levy = levy;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
