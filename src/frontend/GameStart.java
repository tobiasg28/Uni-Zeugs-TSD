/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import dao.BaseDAO;
import dao.GameMapDAO;
import dao.ParticipationDAO;
import dao.ResourceAmountDAO;
import dao.ResourceDAO;
import dao.SquareDAO;
import dao.TroopDAO;
import dao.TroopTypeDAO;
import entities.Base;
import entities.GameMap;
import entities.Participation;
import entities.Resource;
import entities.ResourceAmount;
import entities.Square;
import entities.Troop;
import entities.User;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import storage.DAOException;

/**
 * 
 * @author wi3s3r
 */
public class GameStart {

	// Precondition: Types for Troops, Ressources, Buildings already exist
	static Logger logger = Logger.getLogger(GameStart.class);

	// Precondition: Types for Troops, Ressources, Buildings already exist
	public static long newGame(String name, int maxPlayer)
			throws GameStartException {
		ResourceDAO rDao = new ResourceDAO();
		List<Resource> resources = null;
		try {
			resources = rDao.getAll();
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: getAll Resources", ex);
		}
		if (resources == null || resources.isEmpty()) {
			throw new GameStartException("ERROR: No Ressources defined");
		}

		GameMapDAO mDao = new GameMapDAO();
		SquareDAO sDAO = new SquareDAO();

		GameMap map = new GameMap();
		map.setMaxUsers(maxPlayer);
		map.setName(name);
		List<Square> squares = new ArrayList<Square>();
		for (int x = 0; x < (2 * maxPlayer); x++) {
			for (int y = 0; y < (2 * maxPlayer); y++) {
				Square s = new Square();
				s.setPositionX(x);
				s.setPositionY(y);
				if ((x + y == maxPlayer || x == y) && x % 2 == 0) {
					s.setPrivilegedFor(resources.get(x % resources.size()));
				}
				if ((x == maxPlayer / 2 && y % 2 != 0)
						|| (y == maxPlayer / 2 && x % 2 != 0)) {
					s.setPrivilegedFor(resources.get(x % resources.size()));
				}
				try {
					if (sDAO.create(s)) {
						squares.add(s);
					} else {
						throw new GameStartException(
								"ERROR: create Square returns false");
					}
				} catch (DAOException ex) {
					throw new GameStartException("ERROR: create Square", ex);
				}
			}
		}
		try {
			if (mDao.create(map) && map.getId() != null) {
				/* FIX For associating squares with the right map! */
				for (Square s : squares) {
					s.setMap(map);
					sDAO.update(s);
				}
				map.setSquares(squares);
				mDao.update(map);

				return map.getId();
			} else {
				throw new GameStartException(
						"ERROR: create GameMap returns false");
			}
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: create GameMap", ex);
		}
	}

	// Precondition: Map exists + Types exist
	public static boolean newPlayer(User user, Long GameMapId)
			throws GameStartException {
		GameMapDAO mDao = new GameMapDAO();
		GameMap map = null;
		try {
			map = mDao.get(GameMapId);
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: Read GameMap", ex);
		}
		if (map.getParticipations() != null) {
			for (Participation p : map.getParticipations()) {
				if (user.equals(p.getParticipant())) {
					throw new GameStartException(
							"ERROR: Participation already exists");
				}
			}
		}
		Participation player = new Participation();
		player.setParticipant(user);
		player.setMap(map);
		ResourceDAO rDao = new ResourceDAO();
		List<Resource> resources = null;
		try {
			resources = rDao.getAll();
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: getAll Resources", ex);
		}
		if (resources == null || resources.isEmpty()) {
			throw new GameStartException("ERROR: No Ressources defined");
		}

		ResourceAmountDAO raDao = new ResourceAmountDAO();
		List<ResourceAmount> resourceAmounts = new ArrayList<ResourceAmount>();
		for (Resource r : resources) {
			ResourceAmount ra = new ResourceAmount();
			ra.setAmount(100);
			ra.setResource(r);
			try {
				if (raDao.create(ra)) {
					resourceAmounts.add(ra);
				} else {
					throw new GameStartException(
							"ERROR: create ResourceAmount returns false");
				}
			} catch (DAOException ex) {
				throw new GameStartException("ERROR: create ResourceAmount", ex);
			}
		}
		player.setResources(resourceAmounts);

		List<Base> bases = new ArrayList<Base>();
		BaseDAO bDao = new BaseDAO();
		Base start = new Base();
		start.setStarterBase(true);
		Square s = null;
		s = Acceptance.getSquareForBase(map);
		if (s == null) {
			throw new GameStartException("ERROR: No free Square for a Base");
		}
		start.setSquare(s);
		try {
			if (bDao.create(start)) {
				bases.add(start);
			} else {
				throw new GameStartException(
						"ERROR: create StarterBase returns false");
			}
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: create StarterBase", ex);
		}
		player.setBases(bases);
		List<Troop> troops = new ArrayList<Troop>();
		Troop t = new Troop();
		TroopTypeDAO ttDao = new TroopTypeDAO();
		try {
			t.setUpgradeLevel(ttDao.getAll().get(0));
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: No TroopTypes", ex);
		} catch (Exception ex) {
			throw new GameStartException("ERROR: No TroopTypes", ex);
		}
		t.setCurrentSquare(s);
		TroopDAO tDao = new TroopDAO();
		try {
			if (tDao.create(t)) {
				troops.add(t);
			} else {
				throw new GameStartException(
						"ERROR: create Troop returns false");
			}
		} catch (DAOException ex) {
			throw new GameStartException("ERROR: create Troop", ex);
		}
		player.setTroops(null);
		ParticipationDAO pDao = new ParticipationDAO();
		try {
			if (pDao.create(player)) {
				start.setParticipation(player);
				bDao.update(start);
				t.setParticipation(player);
				tDao.update(t);
				return true;
			} else {
				throw new GameStartException(
						"ERROR: create Participation returns false");
			}
		} catch (DAOException ex) {
			throw new GameStartException(
					"ERROR: create Participation or update", ex);
		}
	}
}
