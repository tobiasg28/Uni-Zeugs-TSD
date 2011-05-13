package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.BaseDAO;

public class BaseTest {

	private BaseDAO dao;
	private Base entity;

	@Before
	public void setUp() throws DAOException {
		dao = new BaseDAO();
		entity = new Base();
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
		entity = new Base();

		GameStep created = new GameStep();
		DAOImpl.getInstance().create(created);
		entity.setCreated(created);

		GameStep destroyed = new GameStep();
		DAOImpl.getInstance().create(destroyed);
		entity.setDestroyed(destroyed);

		Participation participation = new Participation();
		DAOImpl.getInstance().create(participation);
		entity.setParticipation(participation);

		Square square = new Square();
		DAOImpl.getInstance().create(square);
		entity.setSquare(square);

		entity.setStarterBase(true);

		GameStep lastProductionStep = new GameStep();
		DAOImpl.getInstance().create(lastProductionStep);
		entity.setLastProductionStep(lastProductionStep);

		entity.setBuildings(new ArrayList<Building>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Base s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		GameStep created = s.getCreated();
		Assert.assertNotNull(created);

		GameStep destroyed = s.getDestroyed();
		Assert.assertNotNull(destroyed);

		Participation participation = s.getParticipation();
		Assert.assertNotNull(participation);

		Square square = s.getSquare();
		Assert.assertNotNull(square);

		Assert.assertEquals(true, s.getStarterBase());

		GameStep lastProductionStep = s.getLastProductionStep();
		Assert.assertNotNull(lastProductionStep);

	}

	@Test
	public void updatingTest() throws DAOException {
		Base s = new Base();

		s.setCreated(null);

		s.setDestroyed(null);

		s.setParticipation(null);

		s.setSquare(null);

		s.setStarterBase(true);

		s.setLastProductionStep(null);

		s.setBuildings(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Base sCopy = new Base();

		sCopy.setCreated(s.getCreated());

		sCopy.setDestroyed(s.getDestroyed());

		sCopy.setParticipation(s.getParticipation());

		sCopy.setSquare(s.getSquare());

		sCopy.setStarterBase(s.getStarterBase());

		sCopy.setLastProductionStep(s.getLastProductionStep());

		sCopy.setBuildings(s.getBuildings());

		// Change Values of s

		s.setCreated(null);

		s.setDestroyed(null);

		s.setParticipation(null);

		s.setSquare(null);

		s.setStarterBase(false);

		s.setLastProductionStep(null);

		s.setBuildings(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getCreated() == sCopy.getCreated());

		Assert.assertTrue(s.getDestroyed() == sCopy.getDestroyed());

		Assert.assertTrue(s.getParticipation() == sCopy.getParticipation());

		Assert.assertTrue(s.getSquare() == sCopy.getSquare());

		Assert.assertTrue(s.getStarterBase() != sCopy.getStarterBase());

		Assert.assertTrue(s.getLastProductionStep() == sCopy
				.getLastProductionStep());

		Assert.assertTrue(s.getBuildings() == sCopy.getBuildings());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Base s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
