package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_trooptype")
public class TroopType implements Serializable {

	private static final long serialVersionUID = 1L;

	public TroopType() {
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

	private int strength;

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}

	private int speed;

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	@OneToOne()
	private ResourceAmount initialCost;

	public void setInitialCost(ResourceAmount initialCost) {
		this.initialCost = initialCost;
	}

	public ResourceAmount getInitialCost() {
		return initialCost;
	}

	@OneToOne()
	private ResourceAmount upgradeCost;

	public void setUpgradeCost(ResourceAmount upgradeCost) {
		this.upgradeCost = upgradeCost;
	}

	public ResourceAmount getUpgradeCost() {
		return upgradeCost;
	}

}
