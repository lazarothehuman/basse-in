package mz.co.basse.inbasse.accesscontrol.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mz.co.basse.inbasse.core.model.Activable;
import mz.co.basse.inbasse.core.model.Identifiable;

@Entity
@Table(name = "client")
public class Client implements Identifiable, Activable {
	
	private static final long serialVersionUID = -2551634247802365227L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(nullable = false)
	private String name;

	private String address;

	private String phone;

	private String email;
	
	@OneToMany(mappedBy="client")
	private List<Request> requests = new ArrayList<>();
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public Long getId() {
		return id;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
