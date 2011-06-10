package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "swag_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Column(unique = true)
	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	private String fullName;

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	private String adress;

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getAdress() {
		return adress;
	}

	private java.util.TimeZone timezone;

	public void setTimezone(java.util.TimeZone timezone) {
		this.timezone = timezone;
	}

	public java.util.TimeZone getTimezone() {
		return timezone;
	}

	@OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
	private List<Message> outMessages = new ArrayList<Message>();

	public void setOutMessages(List<Message> outMessages) {
		this.outMessages = outMessages;
	}

	public List<Message> getOutMessages() {
		return outMessages;
	}

	@OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
	private List<Message> inMessages = new ArrayList<Message>();

	public void setInMessages(List<Message> inMessages) {
		this.inMessages = inMessages;
	}

	public List<Message> getInMessages() {
		return inMessages;
	}

	@OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
	private List<Participation> participations = new ArrayList<Participation>();

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

}
