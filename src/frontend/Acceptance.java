/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import dao.GameMapDAO;
import dao.ParticipationDAO;
import entities.Base;
import entities.GameMap;
import entities.Participation;
import entities.ResourceAmount;
import entities.Square;
import entities.Troop;
import entities.TroopType;

import java.util.List;
import storage.DAOException;

/**
 * 
 * @author wi3s3r
 */
public class Acceptance {

	public static boolean enoughResource(List<ResourceAmount> request,
			Long participationId) throws AcceptanceException {
		ParticipationDAO dao = new ParticipationDAO();
		Participation p = null;
		try {
			p = dao.get(participationId);
		} catch (DAOException ex) {
			throw new AcceptanceException("ERROR: Read Participation", ex);
		}
		if (p == null) {
			throw new AcceptanceException("ERROR: Participation is null");
		}
		for (ResourceAmount r : request) {
			for (ResourceAmount pr : p.getResources()) {
				if (r.getResource().equals(pr.getResource())) {
					if (r.getAmount() > pr.getAmount()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean existsTroopUpgradeLevel(TroopType type) {
		if (type.getNextLevel() == null) {
			return false;
		}
		return true;
	}

	public static boolean isSquareFree(Square square, Long GameMapId)
			throws AcceptanceException {
		GameMapDAO dao = new GameMapDAO();
		GameMap map = null;
		try {
			map = dao.get(GameMapId);
		} catch (DAOException ex) {
			throw new AcceptanceException("ERROR: Read GameMap", ex);
		}
		if (map == null) {
			throw new AcceptanceException("ERROR: GameMap is null");
		}
		for (Participation p : map.getParticipations()) {
			for (Base b : p.getBases()) {
				if (b.getSquare().equals(square)) {
					return false;
				}
			}
			if (p.getTroops() != null) {
				for (Troop t : p.getTroops()) {
					if (t.getCurrentSquare().equals(square)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static Square getSquareForBase(GameMap map) {
		boolean bmap[][] = new boolean[2 * map.getMaxUsers()][2 * map
				.getMaxUsers()];
		for (int y = 0; y < 2 * map.getMaxUsers(); y++) {
			for (int x = 0; x < 2 * map.getMaxUsers(); x++) {
				bmap[y][x] = true;
			}
		}
		if (map.getParticipations() != null) {
			for (Participation p : map.getParticipations()) {
				if (p.getBases() == null || p.getBases().isEmpty()) {
					continue;
				}
				for (Base b : p.getBases()) {
					int yBegin = b.getSquare().getPositionY() - 2;
					int xBegin = b.getSquare().getPositionX() - 2;
					int yEnd = b.getSquare().getPositionY() + 2;
					int xEnd = b.getSquare().getPositionX() + 2;
					if (yBegin < 0) {
						yBegin = 0;
					}
					if (xBegin < 0) {
						xBegin = 0;
					}
					if (yEnd > 2 * map.getMaxUsers() - 1) {
						yEnd = 2 * map.getMaxUsers() - 1;
					}
					if (xEnd > 2 * map.getMaxUsers() - 1) {
						xEnd = 2 * map.getMaxUsers() - 1;
					}
					for (int y = yBegin; y <= yEnd; y++) {
						for (int x = xBegin; x <= xEnd; x++) {
							bmap[y][x] = false;
						}
					}
				}
			}
		}
		int count = 0;
		for (int y = 0; y < 2 * map.getMaxUsers(); y++) {
			for (int x = 0; x < 2 * map.getMaxUsers(); x++) {
				// System.out.print(bmap[y][x]);
				if (bmap[y][x]) {
					count++;
				}
			}
		}
		if (count == 0) {
			// No free squares
			return null;
		}
		int joice = (2 * map.getMaxUsers()) % count;
		count = -1;
		Square result = null;
		for (int y = 0; y < 2 * map.getMaxUsers(); y++) {
			for (int x = 0; x < 2 * map.getMaxUsers(); x++) {
				if (bmap[y][x]) {
					count++;
					if (count == joice) {
						for (Square s : map.getSquares()) {
							if (s.getPositionY() == y && s.getPositionX() == x) {
								result = s;
							}
						}
						break;
					}
				}
			}
		}
		return result;
	}
}
