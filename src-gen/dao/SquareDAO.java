package dao;

import storage.*;
import entities.Square;
import java.util.List;

public class SquareDAO {

	protected DAOImpl dao;

	public SquareDAO() {
		dao = DAOImpl.getInstance();
	}

	public boolean create(Square object) throws DAOException {
		return dao.create(object);
	}

	public Square get(Long id) throws DAOException {
		return dao.get(id, Square.class);
	}

	public Square update(Square object) throws DAOException {
		return dao.update(object);
	}

	public boolean delete(Long id) throws DAOException {
		return dao.delete(id, Square.class);
	}

	public List<Square> getAll() throws DAOException {
		return dao.getAll(Square.class);
	}

	public List<Square> findByAttributes(
			java.util.Map<String, String> attributes) throws DAOException {
		return dao.findByAttributes(attributes, Square.class);
	}
<<<<<<< HEAD

=======
>>>>>>> 40e632841b2840cbbb1d17c1ce4b9b71bce70243
}
