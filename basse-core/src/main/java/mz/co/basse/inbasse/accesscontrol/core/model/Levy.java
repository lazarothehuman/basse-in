package mz.co.basse.inbasse.accesscontrol.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "levy")
public class Levy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@JoinTable(name = "technician_levy", joinColumns = { @JoinColumn(name = "levy_id") }, inverseJoinColumns = { @JoinColumn(name = "technician_id") })
	private List<Technician> technicians = new ArrayList<>();
	
	@OneToMany(mappedBy="levy")
	private List<LevyItem> items = new ArrayList<>();
	
	@Column(name="notes", nullable=true)
	private String notes;
	
	@Column(name = "occurance_date", nullable = false)
	private Date occuranceDate;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="request_id")
	private Request request;
	
	@Column(nullable=false)
	private String location;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Technician> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<Technician> technicians) {
		this.technicians = technicians;
	}

	public List<LevyItem> getItems() {
		return items;
	}

	public void setItems(List<LevyItem> items) {
		this.items = items;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getOccuranceDate() {
		return occuranceDate;
	}

	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void addItem(LevyItem item) {
		if (item != null) {
			this.items.add(item);
		}
		
	}

	public void addTechnician(Technician technician) {
		if (technician != null) {
			this.technicians.add(technician);
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	} 

}
