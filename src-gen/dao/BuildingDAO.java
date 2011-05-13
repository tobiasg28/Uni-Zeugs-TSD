package dao;

import storage.*;
import entities.Building;
import java.util.List;

public class BuildingDAO {

	protected DAOImpl dao;

	public BuildingDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Building object) throws DAOException {
		return dao.create(object);
	}

	public Building get(Long id) throws DAOException {
		return dao.get(id, Building.class);
	}

	public Building update(Building object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Building.class);
	}

	public List<Building> getAll() throws DAOException {
		return dao.getAll(Building.class);
	}
}
