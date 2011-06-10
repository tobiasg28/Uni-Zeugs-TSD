package dao;

import storage.*;
import entities.BuildingType;
import java.util.List;

public class BuildingTypeDAO {

	protected DAOImpl dao;

	public BuildingTypeDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(BuildingType object) throws DAOException {
		return dao.create(object);
	}

	public BuildingType get(Long id) throws DAOException {
		return dao.get(id, BuildingType.class);
	}

	public BuildingType update(BuildingType object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, BuildingType.class);
	}

	public List<BuildingType> getAll() throws DAOException {
		return dao.getAll(BuildingType.class);
	}

	public List<BuildingType> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, BuildingType.class);
	}
}
