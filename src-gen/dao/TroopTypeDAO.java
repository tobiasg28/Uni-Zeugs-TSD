package dao;

import storage.*;
import entities.TroopType;

import java.util.List;
import java.util.Map;

public class TroopTypeDAO {

	protected DAOImpl dao;

	public TroopTypeDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(TroopType object) throws DAOException {
		return dao.create(object);
	}

	public TroopType get(Long id) throws DAOException {
		return dao.get(id, TroopType.class);
	}

	public TroopType update(TroopType object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, TroopType.class);
	}

	public List<TroopType> getAll() throws DAOException {
		return dao.getAll(TroopType.class);
	}

	public List<TroopType> findByAttributes(Map<String, String> attributes) {
		return dao.findByAttributes(attributes, TroopType.class);
	}
}
