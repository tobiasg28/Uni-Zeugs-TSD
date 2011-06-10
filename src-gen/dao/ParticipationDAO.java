package dao;

import storage.*;
import entities.Participation;

import java.util.List;
import java.util.Map;

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

	public List<Participation> findByAttributes(Map<String, String> attributes) {
		return dao.findByAttributes(attributes, Participation.class);
	}
}
