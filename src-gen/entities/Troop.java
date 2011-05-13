package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_troop")
public class Troop implements Serializable {

	private static final long serialVersionUID = 1L;

	public Troop() {
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
	private TroopType upgradeLevel;

	public void setUpgradeLevel(TroopType upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}

	public TroopType getUpgradeLevel() {
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
	private Participation participation;

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

	public Participation getParticipation() {
		return participation;
	}

	@ManyToOne()
	private Square currentSquare;

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
	}

	public Square getCurrentSquare() {
		return currentSquare;
	}

	@ManyToOne()
	private Square targetSquare;

	public void setTargetSquare(Square targetSquare) {
		this.targetSquare = targetSquare;
	}

	public Square getTargetSquare() {
		return targetSquare;
	}

	@ManyToOne()
	private GameStep movementStart;

	public void setMovementStart(GameStep movementStart) {
		this.movementStart = movementStart;
	}

	public GameStep getMovementStart() {
		return movementStart;
	}

	@ManyToOne()
	private GameStep movementFinish;

	public void setMovementFinish(GameStep movementFinish) {
		this.movementFinish = movementFinish;
	}

	public GameStep getMovementFinish() {
		return movementFinish;
	}

}
