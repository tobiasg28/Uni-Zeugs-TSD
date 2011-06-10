package dao;

import storage.*;
import entities.Base;
import java.util.List;
import java.util.Map;

public class BaseDAO {

	protected DAOImpl dao;

	public BaseDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Base object) throws DAOException {
		return dao.create(object);
	}

	public Base get(Long id) throws DAOException {
		return dao.get(id, Base.class);
	}

	public Base update(Base object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Base.class);
	}

	public List<Base> getAll() throws DAOException {
		return dao.getAll(Base.class);
	}

	public List<Base> findByAttributes(Map<String, String> attributes) {
		return dao.findByAttributes(attributes, Base.class);
	}
}
