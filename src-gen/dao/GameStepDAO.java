package dao;

import storage.*;
import entities.GameStep;
import java.util.List;

public class GameStepDAO {

	protected DAOImpl dao;

	public GameStepDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(GameStep object) throws DAOException {
		return dao.create(object);
	}

	public GameStep get(Long id) throws DAOException {
		return dao.get(id, GameStep.class);
	}

	public GameStep update(GameStep object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, GameStep.class);
	}

	public List<GameStep> getAll() throws DAOException {
		return dao.getAll(GameStep.class);
	}
}
