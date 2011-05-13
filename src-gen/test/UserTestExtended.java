package test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import storage.DAOException;
import dao.UserDAO;
import dao.UserDAOExtended;
import entities.Message;
import entities.Participation;
import entities.User;

public class UserTestExtended {
	private UserDAOExtended dao;
	private User entity;

	@Before
	public void setUp() throws DAOException {
		dao = new UserDAOExtended();
		entity = new User();
	}
	
	@Test
	public void loginTest() throws DAOException {
		entity = new User();
		entity.setUsername("tobi");
		entity.setPassword("tobiii");
		entity.setFullName("BLA");
		entity.setAdress("BLA");
		entity.setTimezone(java.util.TimeZone.getTimeZone("GMT"));
		entity.setOutMessages(new ArrayList<Message>());
		entity.setInMessages(new ArrayList<Message>());
		entity.setParticipations(new ArrayList<Participation>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
		
		Assert.assertNull(dao.login("nobody", "tobiii"));
		Assert.assertNotNull(dao.login("tobi", "tobiii"));
	}
}
