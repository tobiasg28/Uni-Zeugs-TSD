package backend;

import org.apache.log4j.Logger;

import storage.DAOException;
import dao.ResourceDAO;
import entities.Resource;

public class DatabaseInitializer {
	static Logger logger = Logger.getLogger(DatabaseInitializer.class);
	
	public static Resource createResource(String name) {
		Resource r = new Resource();
		r.setName(name);
		return r;
	}
	
	public static void initializeDB() {
		logger.info("Checking existing data...");
		ResourceDAO dao = new ResourceDAO();
		
		try {
			if (dao.getAll().size() > 0) {
				logger.info("Database already initialized. Good!");
				return;
			}
		} catch (DAOException e1) {
			logger.error("Error while trying to read from the Database.");
			e1.printStackTrace();
		}
		
		try {
			dao.create(createResource("Food"));
			dao.create(createResource("Wood"));
			dao.create(createResource("Stone"));
			dao.create(createResource("Gold"));
		} catch (DAOException e) {
			logger.error("Could not create resources");
			e.printStackTrace();
			return;
		}
		
		logger.info("Database successfully initialized.");
	}

}
