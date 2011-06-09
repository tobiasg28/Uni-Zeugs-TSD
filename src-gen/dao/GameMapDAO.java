package dao;

import storage.*;
import entities.GameMap;
import entities.User;

import java.util.List;
import java.util.Map;

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
	
	public List<GameMap> findByAttributes(Map<String, String> attributes){
		return dao.findByAttributes(attributes, GameMap.class);
	}
}
