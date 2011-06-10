package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "swag_cachedinvalidationentry")
public class CachedInvalidationEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	public CachedInvalidationEntry() {
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
	private ItemType targetType;

	public void setTargetType(ItemType targetType) {
		this.targetType = targetType;
	}

	public ItemType getTargetType() {
		return targetType;
	}

}
