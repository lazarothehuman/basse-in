package mz.co.basse.inbasse.accesscontrol.core.model;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import mz.co.basse.inbasse.core.model.Activable;
import mz.co.basse.inbasse.core.model.Identifiable;
import mz.co.basse.inbasse.core.model.Language;


@Entity
@Table(name = "user")
public class User implements Identifiable, Activable, Comparable<User> {

	private static final long serialVersionUID = 6826842634371424449L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Language language;

	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profile_id", nullable = false)
	@ForeignKey(name = "fk_user_profile")
	private Profile profile;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return login.equals(((User) obj).login);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return login.hashCode();
	}

	public int compareTo(User o) {
		return login.compareTo(o.login);
	}

	@Override
	public String toString() {
		return login;
	}

	public boolean isTransactionAccessible(String transactionCode) {
		List<Transaction> matchingTransactions = select(
				profile.getTransactions(),
				having(on(Transaction.class).getCode(),
						equalTo(transactionCode)));
		return !matchingTransactions.isEmpty();
	}
}
