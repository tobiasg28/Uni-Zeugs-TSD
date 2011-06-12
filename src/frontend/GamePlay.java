package frontend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import storage.DAOException;
import swag.Constants;
import dao.BaseDAO;
import dao.BuildingDAO;
import dao.GameStepDAO;
import dao.ParticipationDAO;
import dao.ResourceDAO;
import dao.SquareDAO;
import dao.TroopDAO;
import dao.TroopTypeDAO;
import entities.Base;
import entities.BuildingType;
import entities.GameStep;
import entities.Participation;
import entities.Resource;
import entities.ResourceAmount;
import entities.Square;
import entities.Building;
import entities.Troop;
import entities.TroopType;

public class GamePlay {

	public static void createBase(long participationId, long squareId)
			throws GamePlayException {
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation", e);
		}

		SquareDAO sDao = new SquareDAO();
		Square square = null;
		try {
			square = sDao.get(squareId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Square", e);
		}

		List<Resource> resources = getResourceTypes();
		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		for (Resource r : resources) {
			ResourceAmount ra = new ResourceAmount();
			ra.setAmount(100);
			ra.setResource(r);
			need.add(ra);
		}
		try {
			if (Acceptance.enoughResource(need, participationId)
					&& Acceptance.isSquareFree(square, square.getMap().getId())) {
				for (ResourceAmount ra : p.getResources()) {
					ra.setAmount(ra.getAmount() - 100);
				}
				Base b = new Base();
				b.setBuildings(new ArrayList<Building>());
				b.setParticipation(p);
				b.setSquare(square);
				b.setStarterBase(false);
				b.setDestroyed(null);
				b.setCreated(getGameStep());
				BaseDAO bDao = new BaseDAO();
				try {
					bDao.create(b);
					if (p.getBases() == null) {
						p.setBases(new ArrayList<Base>());
					}
					p.getBases().add(b);
					pDao.update(p);
					square.setBase(b);
					sDao.update(square);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase", e);
				}
				return;
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources", e);
		}
		throw new GamePlayException(
				"ERROR: Not enough Resources or Sqaure isn't free");
	}

	public static void createBuilding(long participationId, long baseId,
			BuildingType buildingType) throws GamePlayException {
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation", e);
		}

		BaseDAO bDao = new BaseDAO();
		Base base = null;
		try {
			base = bDao.get(baseId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Base", e);
		}

		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(buildingType.getInitialCost());
		try {
			if (Acceptance.enoughResource(need, participationId)
					&& base.getBuildings().size() < 4) {
				for (ResourceAmount ra : p.getResources()) {
					if (ra.getResource().equals(
							buildingType.getInitialCost().getResource())) {
						ra.setAmount(ra.getAmount()
								- buildingType.getInitialCost().getAmount());
					}
				}
				Building b = new Building();
				b.setType(buildingType);
				b.setUpgradeLevel(1);
				b.setBase(base);
				b.setCreated(getGameStep());
				try {
					BuildingDAO bbDao = new BuildingDAO();
					bbDao.create(b);
					if (base.getBuildings() == null) {
						base.setBuildings(new ArrayList<Building>());
					}
					base.getBuildings().add(b);
					bDao.update(base);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase", e);
				}
				return;
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources", e);
		}
		throw new GamePlayException("ERROR: Maximum Buildings on Base reached!");
	}

	public static void createTroop(long participationId, long squareId)
			throws GamePlayException {
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation", e);
		}

		SquareDAO sDao = new SquareDAO();
		Square square = null;
		try {
			square = sDao.get(squareId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Square", e);
		}
		List<TroopType> troopTypes = getTroopTypes();
		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(troopTypes.get(troopTypes.size() - 1).getInitialCost());
		try {
			if (Acceptance.enoughResource(need, participationId)) {
				for (ResourceAmount ra : p.getResources()) {
					if (ra.getResource().equals(
							troopTypes.get(troopTypes.size() - 1)
									.getInitialCost().getResource())) {
						ra.setAmount(ra.getAmount()
								- troopTypes.get(troopTypes.size() - 1)
										.getInitialCost().getAmount());
					}
				}
				Troop t = new Troop();
				t.setCurrentSquare(square);
				t.setCreated(getGameStep());
				t.setParticipation(p);
				t.setUpgradeLevel(troopTypes.get(troopTypes.size() - 1));
				try {
					TroopDAO tDao = new TroopDAO();
					tDao.create(t);
					if (p.getTroops() == null) {
						p.setTroops(new ArrayList<Troop>());
					}
					p.getTroops().add(t);
					pDao.update(p);
					if (square.getTroops() == null) {
						square.setTroops(new ArrayList<Troop>());
					}
					square.getTroops().add(t);
					sDao.update(square);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase", e);
				}
				return;
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources", e);
		}
		throw new GamePlayException(
				"ERROR: Not enough Resources or Square isn't free");
	}

	public static void upgradeBuilding(long participationId, long buildingId)
			throws GamePlayException {
		BuildingDAO bDao = new BuildingDAO();
		Building build = null;

		try {
			build = bDao.get(buildingId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Building", e);
		}

		if (build.getUpgradeLevel() > 9) {
			throw new GamePlayException("No more Upgrade possible");
		}

		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation", e);
		}

		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(build.getType().getUpgradeCost());

		try {
			if (Acceptance.enoughResource(need, participationId)) {
				if (build.getLevelUpgradeFinish() == null) {
					reduceResourceForBuildingUpgrade(build, p);
				} else if (build.getLevelUpgradeFinish().getDate().getTime() < System
						.currentTimeMillis()) {
					reduceResourceForBuildingUpgrade(build, p);
				} else {
					throw new GamePlayException(
							"ERROR: An upgrade of this building is already in progress! It will finish on " + build.getLevelUpgradeFinish().getDate());
				}
				build.setLevelUpgradeFinish(getGameStep());
				try {
					bDao.update(build);
					pDao.update(p);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase", e);
				}
				return;
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources", e);
		}
		throw new GamePlayException("ERROR: Not enough Resources");

	}

	private static void reduceResourceForBuildingUpgrade(Building build,
			Participation p) {
		for (ResourceAmount ra : p.getResources()) {
			if (ra.getResource().equals(
					build.getType().getUpgradeCost().getResource())) {
				ra.setAmount(ra.getAmount()
						- build.getType().getUpgradeCost()
								.getAmount());
			}
		}
	}

	public static void upgradeTroop(long participationId, long troopId)
			throws GamePlayException {
		TroopDAO tDao = new TroopDAO();
		Troop troop = null;
		try {
			troop = tDao.get(troopId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Troop", e);
		}

		if (!Acceptance.existsTroopUpgradeLevel(troop.getUpgradeLevel())) {
			throw new GamePlayException("No more Upgrade possible");
		}

		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation", e);
		}

		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(troop.getUpgradeLevel().getUpgradeCost());
		try {
			if (Acceptance.enoughResource(need, participationId)) {
				for (ResourceAmount ra : p.getResources()) {
					if (ra.getResource().equals(
							troop.getUpgradeLevel().getUpgradeCost()
									.getResource())) {
						ra.setAmount(ra.getAmount()
								- troop.getUpgradeLevel().getUpgradeCost()
										.getAmount());
					}
				}
				troop.setLevelUpgradeFinish(getGameStep());
				try {
					tDao.update(troop);
					pDao.update(p);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase", e);
				}
				return;
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources", e);
		}
		throw new GamePlayException("ERROR: Not enough Resources");
	}

	public static void moveTroop(long troopId, long targetSquareId)
			throws GamePlayException {
		TroopDAO tDao = new TroopDAO();
		Troop troop = null;
		try {
			troop = tDao.get(troopId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Troop", e);
		}

		SquareDAO sDao = new SquareDAO();
		Square tsquare = null;
		try {
			tsquare = sDao.get(targetSquareId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Square", e);
		}

		try {
			GameStepDAO gDao = new GameStepDAO();
			GameStep current = new GameStep();
			current.setDate(new Date(new Date().getTime()
					+ getDistanceFactor(troop.getCurrentSquare(), tsquare)
					* Constants.GAMESTEP_DURATION_MS));
			try {
				gDao.create(current);
			} catch (DAOException e) {
				throw new GamePlayException("ERROR: new GameStep", e);
			}
			troop.setTargetSquare(tsquare);
			troop.setMovementFinish(current);
			tDao.update(troop);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Update Troop", e);
		}
	}

	private static int getDistanceFactor(Square c, Square t) {
		return (int) Math.sqrt(Math.pow(c.getPositionX() - t.getPositionX(), 2)
				+ Math.pow(c.getPositionY() - t.getPositionY(), 2));
	}

	private static GameStep getGameStep() throws GamePlayException {
		GameStepDAO gDao = new GameStepDAO();
		GameStep current = new GameStep();
		current.setDate(new Date(new Date().getTime() + 5
				* Constants.GAMESTEP_DURATION_MS));
		try {
			gDao.create(current);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: new GameStep", e);
		}
		return current;
	}

	private static List<Resource> getResourceTypes() throws GamePlayException {
		ResourceDAO rDao = new ResourceDAO();
		List<Resource> resources = null;
		try {
			resources = rDao.getAll();
		} catch (DAOException ex) {
			throw new GamePlayException("ERROR: getAll Resources", ex);
		}
		if (resources == null || resources.isEmpty()) {
			throw new GamePlayException("ERROR: No Ressources defined");
		}
		return resources;
	}

	private static List<TroopType> getTroopTypes() throws GamePlayException {
		TroopTypeDAO rDao = new TroopTypeDAO();
		List<TroopType> resources = null;
		try {
			resources = rDao.getAll();
		} catch (DAOException ex) {
			throw new GamePlayException("ERROR: getAll TroopType", ex);
		}
		if (resources == null || resources.isEmpty()) {
			throw new GamePlayException("ERROR: No TroopType defined");
		}
		return resources;
	}

}
