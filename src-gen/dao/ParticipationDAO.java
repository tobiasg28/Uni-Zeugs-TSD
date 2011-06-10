package dao;

import storage.*;
import entities.Participation;
import java.util.List;

public class ParticipationDAO {

	protected DAOImpl dao;

	public ParticipationDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Participation object) throws DAOException {
		return dao.create(object);
	}

	public Participation get(Long id) throws DAOException {
		return dao.get(id, Participation.class);
	}

	public Participation update(Participation object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Participation.class);
	}

	public List<Participation> getAll() throws DAOException {
		return dao.getAll(Participation.class);
	}

	public List<Participation> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, Participation.class);
	}
<<<<<<< HEAD

=======
>>>>>>> 40e632841b2840cbbb1d17c1ce4b9b71bce70243
}
