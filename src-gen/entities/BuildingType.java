package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_buildingtype")
public class BuildingType implements Serializable {

	private static final long serialVersionUID = 1L;

	public BuildingType() {
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

	private int productionRate;

	public void setProductionRate(int productionRate) {
		this.productionRate = productionRate;
	}

	public int getProductionRate() {
		return productionRate;
	}

	@ManyToOne()
	private Resource productionType;

	public void setProductionType(Resource productionType) {
		this.productionType = productionType;
	}

	public Resource getProductionType() {
		return productionType;
	}

}
