package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.UserDAO;

public class UserTest {

	private UserDAO dao;
	private User entity;

	@Before
	public void setUp() throws DAOException {
		dao = new UserDAO();
		entity = new User();
	}

	@After
	public void tearDown() {
		try {
			if (entity != null && entity.getId() != null)
				dao.delete(entity.getId());
		} catch (DAOException ex) {
			;
		}
	}

	@Test
	public void creatingTest() throws DAOException {
		entity = new User();

		entity.setUsername("BLA");

		entity.setPassword("BLA");

		entity.setFullName("BLA");

		entity.setAdress("BLA");

		entity.setTimezone(java.util.TimeZone.getTimeZone("GMT"));

		entity.setOutMessages(new ArrayList<Message>());

		entity.setInMessages(new ArrayList<Message>());

		entity.setParticipations(new ArrayList<Participation>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		User s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		Assert.assertEquals("BLA", s.getUsername());

		Assert.assertEquals("BLA", s.getPassword());

		Assert.assertEquals("BLA", s.getFullName());

		Assert.assertEquals("BLA", s.getAdress());

		java.util.TimeZone timezone = s.getTimezone();
		Assert.assertNotNull(timezone);

	}

	@Test
	public void updatingTest() throws DAOException {
		User s = new User();

		s.setUsername("BLA");

		s.setPassword("BLA");

		s.setFullName("BLA");

		s.setAdress("BLA");

		s.setTimezone(java.util.TimeZone.getTimeZone("GMT"));

		s.setOutMessages(null);

		s.setInMessages(null);

		s.setParticipations(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		User sCopy = new User();

		sCopy.setUsername(s.getUsername());

		sCopy.setPassword(s.getPassword());

		sCopy.setFullName(s.getFullName());

		sCopy.setAdress(s.getAdress());

		sCopy.setTimezone(s.getTimezone());

		sCopy.setOutMessages(s.getOutMessages());

		sCopy.setInMessages(s.getInMessages());

		sCopy.setParticipations(s.getParticipations());

		// Change Values of s

		s.setUsername("changed");

		s.setPassword("changed");

		s.setFullName("changed");

		s.setAdress("changed");

		s.setTimezone(java.util.TimeZone.getTimeZone("GMT+1"));

		s.setOutMessages(null);

		s.setInMessages(null);

		s.setParticipations(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getUsername() != sCopy.getUsername());

		Assert.assertTrue(s.getPassword() != sCopy.getPassword());

		Assert.assertTrue(s.getFullName() != sCopy.getFullName());

		Assert.assertTrue(s.getAdress() != sCopy.getAdress());

		Assert.assertTrue(s.getTimezone() != sCopy.getTimezone());

		Assert.assertTrue(s.getOutMessages() == sCopy.getOutMessages());

		Assert.assertTrue(s.getInMessages() == sCopy.getInMessages());

		Assert.assertTrue(s.getParticipations() == sCopy.getParticipations());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		User s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
