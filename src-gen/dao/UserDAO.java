package dao;

import storage.*;
import entities.User;
import java.util.List;

public class UserDAO {

	protected DAOImpl dao;

	public UserDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(User object) throws DAOException {
		return dao.create(object);
	}

	public User get(Long id) throws DAOException {
		return dao.get(id, User.class);
	}

	public User update(User object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, User.class);
	}

	public List<User> getAll() throws DAOException {
		return dao.getAll(User.class);
	}

	public List<User> findByAttributes(java.util.Map<String, String> attributes)
			throws DAOException {
		return dao.findByAttributes(attributes, User.class);
	}
}
