package backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import storage.DAOException;
import dao.BuildingTypeDAO;
import dao.ResourceAmountDAO;
import dao.ResourceDAO;
import dao.TroopTypeDAO;
import entities.BuildingType;
import entities.Resource;
import entities.ResourceAmount;
import entities.TroopType;

public class DatabaseInitializer {
	static Logger logger = Logger.getLogger(DatabaseInitializer.class);
	static TroopTypeDAO troopTypeDAO = new TroopTypeDAO();
	static ResourceDAO resourceDAO = new ResourceDAO();
	static ResourceAmountDAO resourceAmountDAO = new ResourceAmountDAO();
	static BuildingTypeDAO buildingTypeDAO = new BuildingTypeDAO();
	
	public static Resource createResource(String name) throws DAOException {
		Resource r = new Resource();
		r.setName(name);
		resourceDAO.create(r);
		return r;
	}
	
	public static ResourceAmount createResourceAmount(Resource resource,
			int amount) throws DAOException {
		ResourceAmount a = new ResourceAmount();
		a.setResource(resource);
		a.setAmount(amount);
		resourceAmountDAO.create(a);
		return a;
	}
	
	public static TroopType createTroopType(int strength, int speed,
			ResourceAmount initial, ResourceAmount upgrade,
			TroopType nextLevel) throws DAOException {
		TroopType t = new TroopType();
		t.setInitialCost(initial);
		t.setUpgradeCost(upgrade);
		t.setStrength(strength);
		t.setSpeed(speed);
		t.setNextLevel(nextLevel);
		troopTypeDAO.create(t);
		return t;
	}
	
	public static BuildingType createBuildingType(Resource resource, int rate,
			ResourceAmount initial, ResourceAmount upgrade, String name) throws DAOException {
		BuildingType b = new BuildingType();
		b.setProductionRate(rate);
		b.setProductionType(resource);
		b.setInitialCost(initial);
		b.setUpgradeCost(upgrade);
		b.setName(name);
		buildingTypeDAO.create(b);
		return b;
	}
	
	public static void initializeDB() throws DatabaseInitializerException {
		logger.info("Initializing database...");
		
		if (!initializeResources()) {
			throw new DatabaseInitializerException("Resources");
		}
		
		if (!initializeTroopTypes()) {
			throw new DatabaseInitializerException("Troop Types");
		}
		
		if (!initializeBuildingTypes()) {
			throw new DatabaseInitializerException("Building Types");
		}
		
		logger.info("Database successfully initialized.");
	}
	
	private static Map<String,Resource> getResources() throws DAOException {
		List<Resource> l;
		l = resourceDAO.getAll();
		Map<String,Resource> resources = new HashMap<String,Resource>();
		for (Resource r: l) {
			resources.put(r.getName(), r);
		}
		return resources;
	}
	
	private static boolean initializeTroopTypes() {		
		try {
			if (troopTypeDAO.getAll().size() > 0) {
				logger.info("Troop Types already initialized. Good!");
				return true;
			}
		} catch (DAOException e) {
			logger.error("Error while trying to read Troop Types from the DB.");
			e.printStackTrace();
			return false;
		}
		
		Map<String,Resource> resources;
		try {
			resources = getResources();
		} catch (DAOException e) {
			logger.error("Error while trying to read Resources from the DB.");
			e.printStackTrace();
			return false;
		}

		TroopType t;
		
		try {
			/* Troop types */
			t = createTroopType(10, 3, createResourceAmount(resources.get("Wood"), 5), createResourceAmount(resources.get("Food"), 10), null);
			t = createTroopType(5, 2, createResourceAmount(resources.get("Stone"), 5), createResourceAmount(resources.get("Gold"), 3), t);
			t = createTroopType(2, 1, createResourceAmount(resources.get("Food"), 2), createResourceAmount(resources.get("Wood"), 4), t);
		} catch (DAOException e) {
			logger.error("Error while trying to create Troop Types.");
			e.printStackTrace();
		}
		
		return true;
	}
	
	private static boolean initializeBuildingTypes() {		
		try {
			if (buildingTypeDAO.getAll().size() > 0) {
				logger.info("Building Types already initialized. Good!");
				return true;
			}
		} catch (DAOException e) {
			logger.error("Error while trying to read Building Types from the DB.");
			e.printStackTrace();
			return false;
		}
		
		Map<String,Resource> resources;
		try {
			resources = getResources();
		} catch (DAOException e) {
			logger.error("Error while trying to read Resources from the DB.");
			e.printStackTrace();
			return false;
		}
				
		try {
			/* Building Types */
			
			/* Hunter */
			createBuildingType(resources.get("Food"), 10,
					createResourceAmount(resources.get("Wood"), 10),
					createResourceAmount(resources.get("Stone"), 2), "Farm");
			
			/* Forester */
			createBuildingType(resources.get("Wood"), 15,
					createResourceAmount(resources.get("Wood"), 15),
					createResourceAmount(resources.get("Stone"), 5), "Lumber Camp");
			
			/* Quarry */
			createBuildingType(resources.get("Stone"), 5,
					createResourceAmount(resources.get("Wood"), 5),
					createResourceAmount(resources.get("Stone"), 20), "Quarry");
			
			/* Gold Mine */
			createBuildingType(resources.get("Gold"), 2,
					createResourceAmount(resources.get("Stone"), 7),
					createResourceAmount(resources.get("Wood"), 20), "Gold Mine");
			
		} catch (DAOException e) {
			logger.error("Error while trying to create Building Types.");
			e.printStackTrace();
		}
		
		return true;
	}

	public static boolean initializeResources() {
		try {
			if (resourceDAO.getAll().size() > 0) {
				logger.info("Resources already initialized. Good!");
				return true;
			}
		} catch (DAOException e1) {
			logger.error("Error while trying to read Resources from the Database.");
			e1.printStackTrace();
			return false;
		}
		
		try {
			createResource("Food");
			createResource("Wood");
			createResource("Stone");
			createResource("Gold");
		} catch (DAOException e) {
			logger.error("Could not create resources");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
