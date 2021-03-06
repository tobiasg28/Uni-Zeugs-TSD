package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "swag_participation")
public class Participation implements Serializable {

	private static final long serialVersionUID = 1L;

	public Participation() {
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

	@ManyToOne()
	private GameStep since;

	public void setSince(GameStep since) {
		this.since = since;
	}

	public GameStep getSince() {
		return since;
	}

	@ManyToOne()
	private GameStep lastLogin;

	public void setLastLogin(GameStep lastLogin) {
		this.lastLogin = lastLogin;
	}

	public GameStep getLastLogin() {
		return lastLogin;
	}

	@ManyToOne()
	private User participant;

	public void setParticipant(User participant) {
		this.participant = participant;
	}

	public User getParticipant() {
		return participant;
	}

	@ManyToOne()
	private GameMap map;

	public void setMap(GameMap map) {
		this.map = map;
	}

	public GameMap getMap() {
		return map;
	}

	@OneToMany(cascade = CascadeType.ALL)
	private List<ResourceAmount> resources = new ArrayList<ResourceAmount>();

	public void setResources(List<ResourceAmount> resources) {
		this.resources = resources;
	}

	public List<ResourceAmount> getResources() {
		return resources;
	}

	@OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
	private List<Troop> troops = new ArrayList<Troop>();

	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}

	public List<Troop> getTroops() {
		return troops;
	}

	@OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
	private List<Base> bases = new ArrayList<Base>();

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public List<Base> getBases() {
		return bases;
	}

}
