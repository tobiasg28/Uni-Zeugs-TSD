package dao;

import storage.*;
import entities.Troop;
import java.util.List;

public class TroopDAO {

	protected DAOImpl dao;

	public TroopDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Troop object) throws DAOException {
		return dao.create(object);
	}

	public Troop get(Long id) throws DAOException {
		return dao.get(id, Troop.class);
	}

	public Troop update(Troop object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Troop.class);
	}

	public List<Troop> getAll() throws DAOException {
		return dao.getAll(Troop.class);
	}

	public List<Troop> findByAttributes(java.util.Map<String, String> attributes)
			throws DAOException {
		return dao.findByAttributes(attributes, Troop.class);
	}

}
