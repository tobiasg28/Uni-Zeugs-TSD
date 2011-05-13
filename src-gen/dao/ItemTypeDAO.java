package dao;

import storage.*;
import entities.ItemType;
import java.util.List;

public class ItemTypeDAO {

	protected DAOImpl dao;

	public ItemTypeDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(ItemType object) throws DAOException {
		return dao.create(object);
	}

	public ItemType get(Long id) throws DAOException {
		return dao.get(id, ItemType.class);
	}

	public ItemType update(ItemType object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, ItemType.class);
	}

	public List<ItemType> getAll() throws DAOException {
		return dao.getAll(ItemType.class);
	}
}
