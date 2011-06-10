package dao;

import storage.*;
import entities.Resource;
import java.util.List;

public class ResourceDAO {

	protected DAOImpl dao;

	public ResourceDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Resource object) throws DAOException {
		return dao.create(object);
	}

	public Resource get(Long id) throws DAOException {
		return dao.get(id, Resource.class);
	}

	public Resource update(Resource object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Resource.class);
	}

	public List<Resource> getAll() throws DAOException {
		return dao.getAll(Resource.class);
	}

	public List<Resource> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, Resource.class);
	}

}
