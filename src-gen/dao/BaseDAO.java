package dao;

import storage.*;
import entities.Base;
import java.util.List;

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
}
