package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.ParticipationDAO;

public class ParticipationTest {

	private ParticipationDAO dao;
	private Participation entity;

	@Before
	public void setUp() throws DAOException {
		dao = new ParticipationDAO();
		entity = new Participation();
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
		entity = new Participation();

		GameStep since = new GameStep();
		DAOImpl.getInstance().create(since);
		entity.setSince(since);

		GameStep lastLogin = new GameStep();
		DAOImpl.getInstance().create(lastLogin);
		entity.setLastLogin(lastLogin);

		User participant = new User();
		DAOImpl.getInstance().create(participant);
		entity.setParticipant(participant);

		GameMap map = new GameMap();
		DAOImpl.getInstance().create(map);
		entity.setMap(map);

		entity.setResources(new ArrayList<ResourceAmount>());

		entity.setTroops(new ArrayList<Troop>());

		entity.setBases(new ArrayList<Base>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Participation s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		GameStep since = s.getSince();
		Assert.assertNotNull(since);

		GameStep lastLogin = s.getLastLogin();
		Assert.assertNotNull(lastLogin);

		User participant = s.getParticipant();
		Assert.assertNotNull(participant);

		GameMap map = s.getMap();
		Assert.assertNotNull(map);

	}

	@Test
	public void updatingTest() throws DAOException {
		Participation s = new Participation();

		s.setSince(null);

		s.setLastLogin(null);

		s.setParticipant(null);

		s.setMap(null);

		s.setResources(null);

		s.setTroops(null);

		s.setBases(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Participation sCopy = new Participation();

		sCopy.setSince(s.getSince());

		sCopy.setLastLogin(s.getLastLogin());

		sCopy.setParticipant(s.getParticipant());

		sCopy.setMap(s.getMap());

		sCopy.setResources(s.getResources());

		sCopy.setTroops(s.getTroops());

		sCopy.setBases(s.getBases());

		// Change Values of s

		s.setSince(null);

		s.setLastLogin(null);

		s.setParticipant(null);

		s.setMap(null);

		s.setResources(null);

		s.setTroops(null);

		s.setBases(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getSince() == sCopy.getSince());

		Assert.assertTrue(s.getLastLogin() == sCopy.getLastLogin());

		Assert.assertTrue(s.getParticipant() == sCopy.getParticipant());

		Assert.assertTrue(s.getMap() == sCopy.getMap());

		Assert.assertTrue(s.getResources() == sCopy.getResources());

		Assert.assertTrue(s.getTroops() == sCopy.getTroops());

		Assert.assertTrue(s.getBases() == sCopy.getBases());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Participation s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
