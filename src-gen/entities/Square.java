package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_square")
public class Square implements Serializable {

	private static final long serialVersionUID = 1L;

	public Square() {
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
	private GameMap map;

	public void setMap(GameMap map) {
		this.map = map;
	}

	public GameMap getMap() {
		return map;
	}

	private int positionX;

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionX() {
		return positionX;
	}

	private int positionY;

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getPositionY() {
		return positionY;
	}

	@ManyToOne()
	private Resource privilegedFor;

	public void setPrivilegedFor(Resource privilegedFor) {
		this.privilegedFor = privilegedFor;
	}

	public Resource getPrivilegedFor() {
		return privilegedFor;
	}

	@OneToOne()
	private Base base;

	public void setBase(Base base) {
		this.base = base;
	}

	public Base getBase() {
		return base;
	}

}
