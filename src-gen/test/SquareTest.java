package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.SquareDAO;

public class SquareTest {

	private SquareDAO dao;
	private Square entity;

	@Before
	public void setUp() throws DAOException {
		dao = new SquareDAO();
		entity = new Square();
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
		entity = new Square();

		GameMap map = new GameMap();
		DAOImpl.getInstance().create(map);
		entity.setMap(map);

		entity.setPositionX(0);

		entity.setPositionY(0);

		Resource privilegedFor = new Resource();
		DAOImpl.getInstance().create(privilegedFor);
		entity.setPrivilegedFor(privilegedFor);

		Building building = new Building();
		DAOImpl.getInstance().create(building);
		entity.setBuilding(building);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Square s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		GameMap map = s.getMap();
		Assert.assertNotNull(map);

		Assert.assertEquals(0, s.getPositionX());

		Assert.assertEquals(0, s.getPositionY());

		Resource privilegedFor = s.getPrivilegedFor();
		Assert.assertNotNull(privilegedFor);

		Building building = s.getBuilding();
		Assert.assertNotNull(building);

	}

	@Test
	public void updatingTest() throws DAOException {
		Square s = new Square();

		s.setMap(null);

		s.setPositionX(0);

		s.setPositionY(0);

		s.setPrivilegedFor(null);

		s.setBuilding(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Square sCopy = new Square();

		sCopy.setMap(s.getMap());

		sCopy.setPositionX(s.getPositionX());

		sCopy.setPositionY(s.getPositionY());

		sCopy.setPrivilegedFor(s.getPrivilegedFor());

		sCopy.setBuilding(s.getBuilding());

		// Change Values of s

		s.setMap(null);

		s.setPositionX(1);

		s.setPositionY(1);

		s.setPrivilegedFor(null);

		s.setBuilding(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getMap() == sCopy.getMap());

		Assert.assertTrue(s.getPositionX() != sCopy.getPositionX());

		Assert.assertTrue(s.getPositionY() != sCopy.getPositionY());

		Assert.assertTrue(s.getPrivilegedFor() == sCopy.getPrivilegedFor());

		Assert.assertTrue(s.getBuilding() == sCopy.getBuilding());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Square s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
