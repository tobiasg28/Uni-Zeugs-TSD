package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.ItemTypeDAO;

public class ItemTypeTest {

	private ItemTypeDAO dao;
	private ItemType entity;

	@Before
	public void setUp() throws DAOException {
		dao = new ItemTypeDAO();
		entity = new ItemType();
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
		entity = new ItemType();

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		ItemType s = dao.get(entity.getId());
		Assert.assertNotNull(s);

	}

	@Test
	public void updatingTest() throws DAOException {
		ItemType s = new ItemType();

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		ItemType sCopy = new ItemType();

		// Change Values of s

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		ItemType s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
