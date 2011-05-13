package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.MessageDAO;

public class MessageTest {

	private MessageDAO dao;
	private Message entity;

	@Before
	public void setUp() throws DAOException {
		dao = new MessageDAO();
		entity = new Message();
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
		entity = new Message();

		User fromUser = new User();
		DAOImpl.getInstance().create(fromUser);
		entity.setFromUser(fromUser);

		User toUser = new User();
		DAOImpl.getInstance().create(toUser);
		entity.setToUser(toUser);

		entity.setTitle("BLA");

		entity.setContent("BLA");

		entity.setDate(new java.util.Date());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Message s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		User fromUser = s.getFromUser();
		Assert.assertNotNull(fromUser);

		User toUser = s.getToUser();
		Assert.assertNotNull(toUser);

		Assert.assertEquals("BLA", s.getTitle());

		Assert.assertEquals("BLA", s.getContent());

		java.util.Date date = s.getDate();
		Assert.assertNotNull(date);

	}

	@Test
	public void updatingTest() throws DAOException {
		Message s = new Message();

		s.setFromUser(null);

		s.setToUser(null);

		s.setTitle("BLA");

		s.setContent("BLA");

		s.setDate(new java.util.Date());

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Message sCopy = new Message();

		sCopy.setFromUser(s.getFromUser());

		sCopy.setToUser(s.getToUser());

		sCopy.setTitle(s.getTitle());

		sCopy.setContent(s.getContent());

		sCopy.setDate(s.getDate());

		// Change Values of s

		s.setFromUser(null);

		s.setToUser(null);

		s.setTitle("changed");

		s.setContent("changed");

		s.setDate(new java.util.Date());

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getFromUser() == sCopy.getFromUser());

		Assert.assertTrue(s.getToUser() == sCopy.getToUser());

		Assert.assertTrue(s.getTitle() != sCopy.getTitle());

		Assert.assertTrue(s.getContent() != sCopy.getContent());

		Assert.assertTrue(s.getDate() != sCopy.getDate());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Message s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
