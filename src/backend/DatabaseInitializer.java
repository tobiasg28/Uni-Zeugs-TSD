package backend;

import storage.DAOException;
import dao.ResourceDAO;
import entities.Resource;

public class DatabaseInitializer {
	
	public static Resource create(String name) {
		Resource r = new Resource();
		r.setName(name);
		return r;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceDAO dao = new ResourceDAO();
		
		try {
			dao.create(create("Food"));
			dao.create(create("Wood"));
			dao.create(create("Stone"));
			dao.create(create("Gold"));
		} catch (DAOException e) {
			System.err.println("DatabaseInitializer error resource");
			e.printStackTrace();
		}
	}

}
