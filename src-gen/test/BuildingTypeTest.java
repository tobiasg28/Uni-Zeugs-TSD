package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.BuildingTypeDAO;

public class BuildingTypeTest {

	private BuildingTypeDAO dao;
	private BuildingType entity;

	@Before
	public void setUp() throws DAOException {
		dao = new BuildingTypeDAO();
		entity = new BuildingType();
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
		entity = new BuildingType();

		ResourceAmount initialCost = new ResourceAmount();
		DAOImpl.getInstance().create(initialCost);
		entity.setInitialCost(initialCost);

		ResourceAmount upgradeCost = new ResourceAmount();
		DAOImpl.getInstance().create(upgradeCost);
		entity.setUpgradeCost(upgradeCost);

		entity.setProductionRate(0);

		Resource productionType = new Resource();
		DAOImpl.getInstance().create(productionType);
		entity.setProductionType(productionType);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		BuildingType s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		ResourceAmount initialCost = s.getInitialCost();
		Assert.assertNotNull(initialCost);

		ResourceAmount upgradeCost = s.getUpgradeCost();
		Assert.assertNotNull(upgradeCost);

		Assert.assertEquals(0, s.getProductionRate());

		Resource productionType = s.getProductionType();
		Assert.assertNotNull(productionType);

	}

	@Test
	public void updatingTest() throws DAOException {
		BuildingType s = new BuildingType();

		s.setInitialCost(null);

		s.setUpgradeCost(null);

		s.setProductionRate(0);

		s.setProductionType(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		BuildingType sCopy = new BuildingType();

		sCopy.setInitialCost(s.getInitialCost());

		sCopy.setUpgradeCost(s.getUpgradeCost());

		sCopy.setProductionRate(s.getProductionRate());

		sCopy.setProductionType(s.getProductionType());

		// Change Values of s

		s.setInitialCost(null);

		s.setUpgradeCost(null);

		s.setProductionRate(1);

		s.setProductionType(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getInitialCost() == sCopy.getInitialCost());

		Assert.assertTrue(s.getUpgradeCost() == sCopy.getUpgradeCost());

		Assert.assertTrue(s.getProductionRate() != sCopy.getProductionRate());

		Assert.assertTrue(s.getProductionType() == sCopy.getProductionType());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		BuildingType s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
