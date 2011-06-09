package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "swag_gamemap")
public class GameMap implements Serializable {

	private static final long serialVersionUID = 1L;

	public GameMap() {
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

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private int maxUsers;

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	@OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
	private List<Participation> participations;

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	@OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
	private List<Square> squares;

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	public List<Square> getSquares() {
		return squares;
	}

}
