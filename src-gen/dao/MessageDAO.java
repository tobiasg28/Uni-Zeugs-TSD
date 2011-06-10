package dao;

import storage.*;
import entities.Message;

import java.util.List;
import java.util.Map;

public class MessageDAO {

	protected DAOImpl dao;

	public MessageDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Message object) throws DAOException {
		return dao.create(object);
	}

	public Message get(Long id) throws DAOException {
		return dao.get(id, Message.class);
	}

	public Message update(Message object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Message.class);
	}

	public List<Message> getAll() throws DAOException {
		return dao.getAll(Message.class);
	}

	public List<Message> findByAttributes(Map<String, String> attributes) {
		return dao.findByAttributes(attributes, Message.class);
	}
}
