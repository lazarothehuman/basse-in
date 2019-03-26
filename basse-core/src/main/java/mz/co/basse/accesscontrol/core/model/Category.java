package mz.co.basse.accesscontrol.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mz.co.basse.core.model.Activable;
import mz.co.basse.core.model.Identifiable;

public class Category implements Identifiable, Activable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4781117101715476799L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public Long getId() {
		return id;
	}
}
