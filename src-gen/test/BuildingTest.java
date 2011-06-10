package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.BuildingDAO;

public class BuildingTest {

	private BuildingDAO dao;
	private Building entity;

	@Before
	public void setUp() throws DAOException {
		dao = new BuildingDAO();
		entity = new Building();
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
		entity = new Building();

		GameStep created = new GameStep();
		DAOImpl.getInstance().create(created);
		entity.setCreated(created);

		Base base = new Base();
		DAOImpl.getInstance().create(base);
		entity.setBase(base);

		BuildingType type = new BuildingType();
		DAOImpl.getInstance().create(type);
		entity.setType(type);

		entity.setUpgradeLevel(0);

		GameStep levelUpgradeStart = new GameStep();
		DAOImpl.getInstance().create(levelUpgradeStart);
		entity.setLevelUpgradeStart(levelUpgradeStart);

		GameStep levelUpgradeFinish = new GameStep();
		DAOImpl.getInstance().create(levelUpgradeFinish);
		entity.setLevelUpgradeFinish(levelUpgradeFinish);

		GameStep lastProductionStep = new GameStep();
		DAOImpl.getInstance().create(lastProductionStep);
		entity.setLastProductionStep(lastProductionStep);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Building s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		GameStep created = s.getCreated();
		Assert.assertNotNull(created);

		Base base = s.getBase();
		Assert.assertNotNull(base);

		BuildingType type = s.getType();
		Assert.assertNotNull(type);

		Assert.assertEquals(0, s.getUpgradeLevel());

		GameStep levelUpgradeStart = s.getLevelUpgradeStart();
		Assert.assertNotNull(levelUpgradeStart);

		GameStep levelUpgradeFinish = s.getLevelUpgradeFinish();
		Assert.assertNotNull(levelUpgradeFinish);

		GameStep lastProductionStep = s.getLastProductionStep();
		Assert.assertNotNull(lastProductionStep);

	}

	@Test
	public void updatingTest() throws DAOException {
		Building s = new Building();

		s.setCreated(null);

		s.setBase(null);

		s.setType(null);

		s.setUpgradeLevel(0);

		s.setLevelUpgradeStart(null);

		s.setLevelUpgradeFinish(null);

		s.setLastProductionStep(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Building sCopy = new Building();

		sCopy.setCreated(s.getCreated());

		sCopy.setBase(s.getBase());

		sCopy.setType(s.getType());

		sCopy.setUpgradeLevel(s.getUpgradeLevel());

		sCopy.setLevelUpgradeStart(s.getLevelUpgradeStart());

		sCopy.setLevelUpgradeFinish(s.getLevelUpgradeFinish());

		sCopy.setLastProductionStep(s.getLastProductionStep());

		// Change Values of s

		s.setCreated(null);

		s.setBase(null);

		s.setType(null);

		s.setUpgradeLevel(1);

		s.setLevelUpgradeStart(null);

		s.setLevelUpgradeFinish(null);

		s.setLastProductionStep(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getCreated() == sCopy.getCreated());

		Assert.assertTrue(s.getBase() == sCopy.getBase());

		Assert.assertTrue(s.getType() == sCopy.getType());

		Assert.assertTrue(s.getUpgradeLevel() != sCopy.getUpgradeLevel());

		Assert.assertTrue(s.getLevelUpgradeStart() == sCopy
				.getLevelUpgradeStart());

		Assert.assertTrue(s.getLevelUpgradeFinish() == sCopy
				.getLevelUpgradeFinish());

		Assert.assertTrue(s.getLastProductionStep() == sCopy
				.getLastProductionStep());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Building s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
