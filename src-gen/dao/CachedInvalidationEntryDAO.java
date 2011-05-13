package dao;

import storage.*;
import entities.CachedInvalidationEntry;
import java.util.List;

public class CachedInvalidationEntryDAO {

	protected DAOImpl dao;

	public CachedInvalidationEntryDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(CachedInvalidationEntry object) throws DAOException {
		return dao.create(object);
	}

	public CachedInvalidationEntry get(Long id) throws DAOException {
		return dao.get(id, CachedInvalidationEntry.class);
	}

	public CachedInvalidationEntry update(CachedInvalidationEntry object)
			throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, CachedInvalidationEntry.class);
	}

	public List<CachedInvalidationEntry> getAll() throws DAOException {
		return dao.getAll(CachedInvalidationEntry.class);
	}
}
