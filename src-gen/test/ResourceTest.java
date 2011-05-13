package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.ResourceDAO;

public class ResourceTest {

	private ResourceDAO dao;
	private Resource entity;

	@Before
	public void setUp() throws DAOException {
		dao = new ResourceDAO();
		entity = new Resource();
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
		entity = new Resource();

		entity.setName("BLA");

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		Resource s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		Assert.assertEquals("BLA", s.getName());

	}

	@Test
	public void updatingTest() throws DAOException {
		Resource s = new Resource();

		s.setName("BLA");

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		Resource sCopy = new Resource();

		sCopy.setName(s.getName());

		// Change Values of s

		s.setName("changed");

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getName() != sCopy.getName());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		Resource s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
