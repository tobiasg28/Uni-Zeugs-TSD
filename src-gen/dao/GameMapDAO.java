package dao;

import storage.*;
import entities.GameMap;
import java.util.List;

public class GameMapDAO {

	protected DAOImpl dao;

	public GameMapDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(GameMap object) throws DAOException {
		return dao.create(object);
	}

	public GameMap get(Long id) throws DAOException {
		return dao.get(id, GameMap.class);
	}

	public GameMap update(GameMap object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, GameMap.class);
	}

	public List<GameMap> getAll() throws DAOException {
		return dao.getAll(GameMap.class);
	}

	public List<GameMap> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, GameMap.class);
	}

}
