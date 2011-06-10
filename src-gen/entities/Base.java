package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "swag_base")
public class Base implements Serializable {

	private static final long serialVersionUID = 1L;

	public Base() {
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
	private GameStep created;

	public void setCreated(GameStep created) {
		this.created = created;
	}

	public GameStep getCreated() {
		return created;
	}

	@ManyToOne()
	private GameStep destroyed;

	public void setDestroyed(GameStep destroyed) {
		this.destroyed = destroyed;
	}

	public GameStep getDestroyed() {
		return destroyed;
	}

	@ManyToOne()
	private Participation participation;

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

	public Participation getParticipation() {
		return participation;
	}

	@OneToOne(mappedBy = "base")
	private Square square;

	public void setSquare(Square square) {
		this.square = square;
	}

	public Square getSquare() {
		return square;
	}

	private boolean starterBase;

	public void setStarterBase(boolean starterBase) {
		this.starterBase = starterBase;
	}

	public boolean getStarterBase() {
		return starterBase;
	}

	@ManyToOne()
	private GameStep lastProductionStep;

	public void setLastProductionStep(GameStep lastProductionStep) {
		this.lastProductionStep = lastProductionStep;
	}

	public GameStep getLastProductionStep() {
		return lastProductionStep;
	}

	@OneToMany(mappedBy = "base", cascade = CascadeType.ALL)
	private List<Building> buildings = new ArrayList<Building>();

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

}
