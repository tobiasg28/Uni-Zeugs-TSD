package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.TroopDAO;

public class TroopTest {

	private TroopDAO dao;
	private Troop entity;

	@Before
	public void setUp() throws DAOException {
		dao = new TroopDAO();
		entity = new Troop();
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
		entity = new Troop();

		GameStep created = new GameStep();
		DAOImpl.getInstance().create(created);
		entity.setCreated(created);

		TroopType upgradeLevel = new TroopType();
		DAOImpl.getInstance().create(upgradeLevel);
		entity.setUpgradeLevel(upgradeLevel);

		GameStep levelUpgradeStart = new GameStep();
		DAOImpl.getInstance().create(levelUpgradeStart);
		entity.setLevelUpgradeStart(levelUpgradeStart);

		GameStep levelUpgradeFinish = new GameStep();
		DAOImpl.getInstance().create(levelUpgradeFinish);
		entity.setLevelUpgradeFinish(levelUpgradeFinish);

		Participation participation = new Participation();
		DAOImpl.getInstance().create(participation);
		entity.setParticipation(participation);

		Square currentSquare = new Square();
		DAOImpl.getInstance().create(currentSquare);
		entity.setCurrentSquare(currentSquare);

		Square targetSquare = new Square();
		DAOImpl.getInstance().create(targetSquare);
		entity.setTargetSquare(targetSquare);

		GameStep movementStart = new GameStep();
		DAOImpl.getInstance().create(movementStart);
		entity.setMovementStart(movementStart);

		GameStep movementFinish = new GameStep();
		DAOImpl.getInstance().create(movementFinish);
		entity.setMovementFinish(movementFinish);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Troop s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		GameStep created = s.getCreated();
		Assert.assertNotNull(created);

		TroopType upgradeLevel = s.getUpgradeLevel();
		Assert.assertNotNull(upgradeLevel);

		GameStep levelUpgradeStart = s.getLevelUpgradeStart();
		Assert.assertNotNull(levelUpgradeStart);

		GameStep levelUpgradeFinish = s.getLevelUpgradeFinish();
		Assert.assertNotNull(levelUpgradeFinish);

		Participation participation = s.getParticipation();
		Assert.assertNotNull(participation);

		Square currentSquare = s.getCurrentSquare();
		Assert.assertNotNull(currentSquare);

		Square targetSquare = s.getTargetSquare();
		Assert.assertNotNull(targetSquare);

		GameStep movementStart = s.getMovementStart();
		Assert.assertNotNull(movementStart);

		GameStep movementFinish = s.getMovementFinish();
		Assert.assertNotNull(movementFinish);

	}

	@Test
	public void updatingTest() throws DAOException {
		Troop s = new Troop();

		s.setCreated(null);

		s.setUpgradeLevel(null);

		s.setLevelUpgradeStart(null);

		s.setLevelUpgradeFinish(null);

		s.setParticipation(null);

		s.setCurrentSquare(null);

		s.setTargetSquare(null);

		s.setMovementStart(null);

		s.setMovementFinish(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Troop sCopy = new Troop();

		sCopy.setCreated(s.getCreated());

		sCopy.setUpgradeLevel(s.getUpgradeLevel());

		sCopy.setLevelUpgradeStart(s.getLevelUpgradeStart());

		sCopy.setLevelUpgradeFinish(s.getLevelUpgradeFinish());

		sCopy.setParticipation(s.getParticipation());

		sCopy.setCurrentSquare(s.getCurrentSquare());

		sCopy.setTargetSquare(s.getTargetSquare());

		sCopy.setMovementStart(s.getMovementStart());

		sCopy.setMovementFinish(s.getMovementFinish());

		// Change Values of s

		s.setCreated(null);

		s.setUpgradeLevel(null);

		s.setLevelUpgradeStart(null);

		s.setLevelUpgradeFinish(null);

		s.setParticipation(null);

		s.setCurrentSquare(null);

		s.setTargetSquare(null);

		s.setMovementStart(null);

		s.setMovementFinish(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getCreated() == sCopy.getCreated());

		Assert.assertTrue(s.getUpgradeLevel() == sCopy.getUpgradeLevel());

		Assert.assertTrue(s.getLevelUpgradeStart() == sCopy
				.getLevelUpgradeStart());

		Assert.assertTrue(s.getLevelUpgradeFinish() == sCopy
				.getLevelUpgradeFinish());

		Assert.assertTrue(s.getParticipation() == sCopy.getParticipation());

		Assert.assertTrue(s.getCurrentSquare() == sCopy.getCurrentSquare());

		Assert.assertTrue(s.getTargetSquare() == sCopy.getTargetSquare());

		Assert.assertTrue(s.getMovementStart() == sCopy.getMovementStart());

		Assert.assertTrue(s.getMovementFinish() == sCopy.getMovementFinish());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Troop s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
