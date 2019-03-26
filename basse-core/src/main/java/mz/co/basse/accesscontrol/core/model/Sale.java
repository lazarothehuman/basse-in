package mz.co.basse.accesscontrol.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sale")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name = "invoice_number", nullable = false)
	private String invoiceNumber;
	
	@Column(name = "cost", nullable = false)
	private BigDecimal totalCost = BigDecimal.ZERO;
	
	@Column(name = "paid_amount", nullable = false)
	private BigDecimal paidAmount = BigDecimal.ZERO;

	@Column(name = "received_amount", nullable = false)
	private BigDecimal receivedAmount = BigDecimal.ZERO;

	@Column(name = "change_amount", nullable = false)
	private BigDecimal change = BigDecimal.ZERO;
	
	@Column(name = "payment_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus = PaymentStatus.OPEN;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User registrator;
	
	@OneToMany(mappedBy = "sale")
	private List<ProductSaleItem> productSaleItems = new ArrayList<>();
	
//	@Column(nullable = false, columnDefinition = "bit")
//	private boolean active = true;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getReceiveAmount() {
		return receivedAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receivedAmount = receiveAmount;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getId() {
		return id;
	}
	
}
