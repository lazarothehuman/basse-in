package mz.co.basse.accesscontrol.core.model;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import mz.co.basse.core.model.Identifiable;


@Entity
@Table(name = "company")
public class Company implements Identifiable {

	private static final long serialVersionUID = 2256837359767347402L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String nuit;

	@Column(nullable = false)
	private String email;

	@Column(name = "logo", columnDefinition = "mediumblob")
	private byte[] logo;

	@Column(name = "vat_tax", nullable = false)
	private BigDecimal vatTax;

	@Column(name = "invoice_note")
	private String invoiceNote;

	@Column(name="bank_details", nullable=false)
	private String bankDetails;


	@Override
	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNuit() {
		return nuit;
	}

	public void setNuit(String nuit) {
		this.nuit = nuit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvoiceNote() {
		return invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}

	public BigDecimal getVatTax() {
		return vatTax;
	}

	public void setVatTax(BigDecimal vatTax) {
		this.vatTax = vatTax;
	}


	public String getBankDetails() {
		return bankDetails;
	}


	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}


	public void setId(Long id) {
		this.id = id;
	}

}
