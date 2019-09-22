package mz.co.basse.inbasse.accesscontrol.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, name = "client_id")
	private Client client;

	@Column(name = "request_date", nullable = false)
	private Date requestDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RequestStatus status;

	@OneToMany(mappedBy = "request", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Levy> levies = new ArrayList<>();

	@OneToMany(mappedBy = "request")
	private List<RequestItem> items = new ArrayList<>();

	@Column(name = "additional_information", nullable = true)
	private String additionalInformation;

	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public List<Levy> getLevies() {
		return levies;
	}

	public void setLevies(List<Levy> levies) {
		this.levies = levies;
	}

	public List<RequestItem> getItems() {
		return items;
	}

	public void setItems(List<RequestItem> items) {
		this.items = items;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void addItem(RequestItem item) {
		if (item != null) {
			this.items.add(item);
		}
	}

}
