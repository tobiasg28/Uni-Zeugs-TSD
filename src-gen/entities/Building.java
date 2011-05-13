package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_building")
public class Building implements Serializable {

	private static final long serialVersionUID = 1L;

	public Building() {
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
	private Base base;

	public void setBase(Base base) {
		this.base = base;
	}

	public Base getBase() {
		return base;
	}

	@OneToOne()
	private Square square;

	public void setSquare(Square square) {
		this.square = square;
	}

	public Square getSquare() {
		return square;
	}

	@ManyToOne()
	private BuildingType type;

	public void setType(BuildingType type) {
		this.type = type;
	}

	public BuildingType getType() {
		return type;
	}

	private int upgradeLevel;

	public void setUpgradeLevel(int upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}

	public int getUpgradeLevel() {
		return upgradeLevel;
	}

	@ManyToOne()
	private GameStep levelUpgradeStart;

	public void setLevelUpgradeStart(GameStep levelUpgradeStart) {
		this.levelUpgradeStart = levelUpgradeStart;
	}

	public GameStep getLevelUpgradeStart() {
		return levelUpgradeStart;
	}

	@ManyToOne()
	private GameStep levelUpgradeFinish;

	public void setLevelUpgradeFinish(GameStep levelUpgradeFinish) {
		this.levelUpgradeFinish = levelUpgradeFinish;
	}

	public GameStep getLevelUpgradeFinish() {
		return levelUpgradeFinish;
	}

	@ManyToOne()
	private GameStep lastProductionStep;

	public void setLastProductionStep(GameStep lastProductionStep) {
		this.lastProductionStep = lastProductionStep;
	}

	public GameStep getLastProductionStep() {
		return lastProductionStep;
	}

}
