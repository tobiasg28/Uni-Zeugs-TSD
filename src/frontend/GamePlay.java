package frontend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import storage.DAOException;
import swag.Constants;
import dao.BaseDAO;
import dao.BuildingDAO;
import dao.BuildingTypeDAO;
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
	
	public static void createBase(long participationId, long squareId) throws GamePlayException{
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation",e);
		}
		
		SquareDAO sDao = new SquareDAO();
		Square square = null;
		try {
			square = sDao.get(squareId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Square",e);
		}
		
		List<Resource> resources = getResourceTypes();
		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		for(Resource r : resources){
			ResourceAmount ra = new ResourceAmount();
			ra.setAmount(500);
			ra.setResource(r);
			need.add(ra);
		}
		try {
			if(Acceptance.enoughResource(need, participationId) && Acceptance.isSquareFree(square, square.getMap().getId())){
				for(ResourceAmount ra : p.getResources()){
					ra.setAmount(ra.getAmount() - 500);
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
					if(p.getBases() == null){
						p.setBases(new ArrayList<Base>());
					}
					p.getBases().add(b);
					pDao.update(p);
					square.setBase(b);
					sDao.update(square);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase",e);
				}
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources",e);
		}
		throw new GamePlayException("ERROR: Not enough Resources or Sqaure isn't free");
	}

	public static void createBuilding(long participationId, long baseId, BuildingType buildingType) throws GamePlayException{
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation",e);
		}
		
		BaseDAO bDao = new BaseDAO();
		Base base = null;
		try {
			base = bDao.get(baseId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Base",e);
		}
		
		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(buildingType.getInitialCost());
		try {
			if(Acceptance.enoughResource(need, participationId) && base.getBuildings().size() < 4){
				for(ResourceAmount ra : p.getResources()){
					if(ra.getResource().equals(buildingType.getInitialCost().getResource())){
						ra.setAmount(ra.getAmount() - buildingType.getInitialCost().getAmount());
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
					if(base.getBuildings() == null){
						base.setBuildings(new ArrayList<Building>());
					}
					base.getBuildings().add(b);
					bDao.update(base);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase",e);
				}
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources",e);
		}
		throw new GamePlayException("ERROR: Not enough Resources or Base isn't free");
	}
	
	public static void createTroop(long participationId, long squareId) throws GamePlayException{
		ParticipationDAO pDao = new ParticipationDAO();
		Participation p = null;
		try {
			p = pDao.get(participationId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Participation",e);
		}
		
		SquareDAO sDao = new SquareDAO();
		Square square = null;
		try {
			square = sDao.get(squareId);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: Read Sqaure",e);
		}
		List<TroopType> troopTypes = getTroopTypes();
		List<ResourceAmount> need = new ArrayList<ResourceAmount>();
		need.add(troopTypes.get(0).getInitialCost());
		try {
			if(Acceptance.enoughResource(need, participationId) && Acceptance.isSquareFree(square, square.getMap().getId())){
				for(ResourceAmount ra : p.getResources()){
					if(ra.getResource().equals(troopTypes.get(0).getInitialCost().getResource())){
						ra.setAmount(ra.getAmount() - troopTypes.get(0).getInitialCost().getAmount());
					}
				}
				Troop t = new Troop();
				t.setCurrentSquare(square);
				t.setCreated(getGameStep());
				t.setParticipation(p);
				t.setUpgradeLevel(troopTypes.get(0));
				try {
					TroopDAO tDao = new TroopDAO();
					tDao.create(t);
					if(p.getTroops() == null){
						p.setTroops(new ArrayList<Troop>());
					}
					p.getTroops().add(t);
					pDao.update(p);
					//square.setTroop(t);
					//sDao.update(square);
				} catch (DAOException e) {
					throw new GamePlayException("ERROR: Update DataBase",e);
				}
			}
		} catch (AcceptanceException e) {
			throw new GamePlayException("ERROR: Not enough Resources",e);
		}
		throw new GamePlayException("ERROR: Not enough Resources or Base isn't free");
	}
	
	public static void upgradeBuilding(){
		
	}
	
	public static void upgradeTroop(){
		
	}
	
	public static void moveTroop(){
		
	}
	
	private static GameStep getGameStep() throws GamePlayException{
		GameStepDAO gDao = new GameStepDAO();
		GameStep current = new GameStep();
		current.setDate(new Date(new Date().getTime() + 5*Constants.GAMESTEP_DURATION_MS));
		try {
			gDao.create(current);
		} catch (DAOException e) {
			throw new GamePlayException("ERROR: new GameStep",e);
		}
		return current;
	}
	
	private static List<Resource> getResourceTypes() throws GamePlayException{
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
	
	private static List<TroopType> getTroopTypes() throws GamePlayException{
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
	
	private static List<BuildingType> getBuildingTypes() throws GamePlayException{
		BuildingTypeDAO rDao = new BuildingTypeDAO();
		List<BuildingType> resources = null;
		try {
			resources = rDao.getAll();
		} catch (DAOException ex) {
			throw new GamePlayException("ERROR: getAll BuildingType", ex);
		}
		if (resources == null || resources.isEmpty()) {
			throw new GamePlayException("ERROR: No BuildingType defined");
		}
		return resources;
	}
}
