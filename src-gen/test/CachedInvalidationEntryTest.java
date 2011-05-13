package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.CachedInvalidationEntryDAO;

public class CachedInvalidationEntryTest {

	private CachedInvalidationEntryDAO dao;
	private CachedInvalidationEntry entity;

	@Before
	public void setUp() throws DAOException {
		dao = new CachedInvalidationEntryDAO();
		entity = new CachedInvalidationEntry();
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
		entity = new CachedInvalidationEntry();

		ItemType targetType = new ItemType();
		DAOImpl.getInstance().create(targetType);
		entity.setTargetType(targetType);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		CachedInvalidationEntry s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		ItemType targetType = s.getTargetType();
		Assert.assertNotNull(targetType);

	}

	@Test
	public void updatingTest() throws DAOException {
		CachedInvalidationEntry s = new CachedInvalidationEntry();

		s.setTargetType(null);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		CachedInvalidationEntry sCopy = new CachedInvalidationEntry();

		sCopy.setTargetType(s.getTargetType());

		// Change Values of s

		s.setTargetType(null);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getTargetType() == sCopy.getTargetType());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		CachedInvalidationEntry s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
