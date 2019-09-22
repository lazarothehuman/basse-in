package mz.co.basse.inbasse.finance.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.User;

@Table(name="sale")
@Entity
public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date", nullable = false)
	@Type(type = "date")
	private Date date;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User registrator;

	@ManyToOne
	@JoinColumn(nullable = false, name = "client_id")
	private Client client;

	@Column(nullable = false, name = "total_cost")
	private BigDecimal totalCost = BigDecimal.ZERO;

	@OneToMany(mappedBy = "sale", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<SaleItem> items = new ArrayList<>();

	@Column(columnDefinition = "bit", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getRegistrator() {
		return registrator;
	}

	public void setRegistrator(User registrator) {
		this.registrator = registrator;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void setItems(List<SaleItem> items) {
		this.items = items;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
