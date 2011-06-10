package dao;

import storage.*;
import entities.TroopType;
import java.util.List;

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

	public List<TroopType> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, TroopType.class);
	}
<<<<<<< HEAD

=======
>>>>>>> 40e632841b2840cbbb1d17c1ce4b9b71bce70243
}
