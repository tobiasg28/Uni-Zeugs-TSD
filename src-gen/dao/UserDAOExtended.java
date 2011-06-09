package dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import entities.User;

public class UserDAOExtended extends UserDAO {
	public User login(String username, String password) {
		Query query = dao.getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		try {
			User user = (User)query.getSingleResult();
			return user;
		} catch (NoResultException ex) {
			return null;
		}
            return null;
	}
}
