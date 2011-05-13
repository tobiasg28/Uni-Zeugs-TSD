package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.GameStepDAO;

public class GameStepTest {

	private GameStepDAO dao;
	private GameStep entity;

	@Before
	public void setUp() throws DAOException {
		dao = new GameStepDAO();
		entity = new GameStep();
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
		entity = new GameStep();

		entity.setDate(new java.util.Date());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		GameStep s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		java.util.Date date = s.getDate();
		Assert.assertNotNull(date);

	}

	@Test
	public void updatingTest() throws DAOException {
		GameStep s = new GameStep();

		s.setDate(new java.util.Date());

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		GameStep sCopy = new GameStep();

		sCopy.setDate(s.getDate());

		// Change Values of s

		s.setDate(new java.util.Date());

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getDate() != sCopy.getDate());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		GameStep s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
