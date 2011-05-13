package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.GameMapDAO;

public class GameMapTest {

	private GameMapDAO dao;
	private GameMap entity;

	@Before
	public void setUp() throws DAOException {
		dao = new GameMapDAO();
		entity = new GameMap();
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
		entity = new GameMap();

		entity.setName("BLA");

		entity.setMaxUsers(0);

		entity.setParticipations(new ArrayList<Participation>());

		entity.setSquares(new ArrayList<Square>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		GameMap s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		Assert.assertEquals("BLA", s.getName());

		Assert.assertEquals(0, s.getMaxUsers());

	}

	@Test
	public void updatingTest() throws DAOException {
		GameMap s = new GameMap();

		s.setName("BLA");

		s.setMaxUsers(0);

		s.setParticipations(null);

		s.setSquares(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		GameMap sCopy = new GameMap();

		sCopy.setName(s.getName());

		sCopy.setMaxUsers(s.getMaxUsers());

		sCopy.setParticipations(s.getParticipations());

		sCopy.setSquares(s.getSquares());

		// Change Values of s

		s.setName("changed");

		s.setMaxUsers(1);

		s.setParticipations(null);

		s.setSquares(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getName() != sCopy.getName());

		Assert.assertTrue(s.getMaxUsers() != sCopy.getMaxUsers());

		Assert.assertTrue(s.getParticipations() == sCopy.getParticipations());

		Assert.assertTrue(s.getSquares() == sCopy.getSquares());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		GameMap s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
