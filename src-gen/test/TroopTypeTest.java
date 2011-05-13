package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.TroopTypeDAO;

public class TroopTypeTest {

	private TroopTypeDAO dao;
	private TroopType entity;

	@Before
	public void setUp() throws DAOException {
		dao = new TroopTypeDAO();
		entity = new TroopType();
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
		entity = new TroopType();

		entity.setStrength(0);

		entity.setSpeed(0);

		ResourceAmount initialCost = new ResourceAmount();
		DAOImpl.getInstance().create(initialCost);
		entity.setInitialCost(initialCost);

		ResourceAmount upgradeCost = new ResourceAmount();
		DAOImpl.getInstance().create(upgradeCost);
		entity.setUpgradeCost(upgradeCost);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		TroopType s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		Assert.assertEquals(0, s.getStrength());

		Assert.assertEquals(0, s.getSpeed());

		ResourceAmount initialCost = s.getInitialCost();
		Assert.assertNotNull(initialCost);

		ResourceAmount upgradeCost = s.getUpgradeCost();
		Assert.assertNotNull(upgradeCost);

	}

	@Test
	public void updatingTest() throws DAOException {
		TroopType s = new TroopType();

		s.setStrength(0);

		s.setSpeed(0);

		s.setInitialCost(null);

		s.setUpgradeCost(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		TroopType sCopy = new TroopType();

		sCopy.setStrength(s.getStrength());

		sCopy.setSpeed(s.getSpeed());

		sCopy.setInitialCost(s.getInitialCost());

		sCopy.setUpgradeCost(s.getUpgradeCost());

		// Change Values of s

		s.setStrength(1);

		s.setSpeed(1);

		s.setInitialCost(null);

		s.setUpgradeCost(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getStrength() != sCopy.getStrength());

		Assert.assertTrue(s.getSpeed() != sCopy.getSpeed());

		Assert.assertTrue(s.getInitialCost() == sCopy.getInitialCost());

		Assert.assertTrue(s.getUpgradeCost() == sCopy.getUpgradeCost());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		TroopType s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
