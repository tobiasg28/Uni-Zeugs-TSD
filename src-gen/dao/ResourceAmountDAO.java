package dao;

import storage.*;
import entities.ResourceAmount;
import java.util.List;

public class ResourceAmountDAO {

	protected DAOImpl dao;

	public ResourceAmountDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(ResourceAmount object) throws DAOException {
		return dao.create(object);
	}

	public ResourceAmount get(Long id) throws DAOException {
		return dao.get(id, ResourceAmount.class);
	}

	public ResourceAmount update(ResourceAmount object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, ResourceAmount.class);
	}

	public List<ResourceAmount> getAll() throws DAOException {
		return dao.getAll(ResourceAmount.class);
	}
}
