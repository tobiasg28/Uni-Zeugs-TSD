package dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import entities.User;

public class UserDAOExtended extends UserDAO {
	public User login(String username, String password) {
		Query query = dao.getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
		query.setParameter("username", username);
		byte[] byteofPassword = null;
		MessageDigest md = null;
		try {
			byteofPassword = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] digest = md.digest(byteofPassword);
		password = new String(digest);
		query.setParameter("password", password);
		try {
			User user = (User)query.getSingleResult();
			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}
}
