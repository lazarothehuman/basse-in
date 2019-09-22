package mz.co.basse.inbasse.finance.core.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import mz.co.basse.inbasse.accesscontrol.core.model.Client;
import mz.co.basse.inbasse.accesscontrol.core.model.User;
import mz.co.basse.inbasse.core.model.Activable;
import mz.co.basse.inbasse.core.model.Identifiable;

@Entity
@Table(name = "payment")
public class Payment implements Identifiable, Activable {

	private static final long serialVersionUID = 2630592248018350610L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "receipt_number", nullable = false)
	private String receiptNumber;

	@Column(nullable = false)
	private BigDecimal value;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Enumerated(EnumType.STRING)
	@Column(name = "form_of_payment", nullable = false)
	private FormOfPayment formOfPayment;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;

	@Column(name = "cheque_number")
	private String chequeNumber;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

//	@OneToOne
//	@JoinColumn(name = "sale_id")
//	private Sale sale;

	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public FormOfPayment getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(FormOfPayment formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Payment) {
			Payment other = (Payment) obj;
			return new EqualsBuilder().append(receiptNumber,
					other.receiptNumber).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(receiptNumber).toHashCode();
	}
}
