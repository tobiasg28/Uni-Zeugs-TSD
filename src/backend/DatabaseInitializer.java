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
			if (dao.getAll().size() > 0) {
				System.out.println("Hey, Rocker - you already got some resources. Good for you!");
				return;
			}
		} catch (DAOException e1) {
			System.err.println("What the Fax? Cannot READ from the DB? ORLY?!??!?!");
			e1.printStackTrace();
		}
		
		try {
			dao.create(create("Food"));
			dao.create(create("Wood"));
			dao.create(create("Stone"));
			dao.create(create("Gold"));
		} catch (DAOException e) {
			System.err.println("DatabaseInitializer error resource");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Domination! We got some resources in the DB!");
	}

}
