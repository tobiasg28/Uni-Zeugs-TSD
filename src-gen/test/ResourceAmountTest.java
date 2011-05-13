package test;

import junit.framework.Assert;
import storage.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

import entities.*;
import dao.ResourceAmountDAO;

public class ResourceAmountTest {

	private ResourceAmountDAO dao;
	private ResourceAmount entity;

	@Before
	public void setUp() throws DAOException {
		dao = new ResourceAmountDAO();
		entity = new ResourceAmount();
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
		entity = new ResourceAmount();

		Resource resource = new Resource();
		DAOImpl.getInstance().create(resource);
		entity.setResource(resource);

		entity.setAmount(0);

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
	}

	@Test
	public void readingTest() throws DAOException {
		creatingTest();
		ResourceAmount s = dao.get(entity.getId());
		Assert.assertNotNull(s);

		Resource resource = s.getResource();
		Assert.assertNotNull(resource);

		Assert.assertEquals(0, s.getAmount());

	}

	@Test
	public void updatingTest() throws DAOException {
		ResourceAmount s = new ResourceAmount();

		s.setResource(null);

		s.setAmount(0);

		Assert.assertTrue(dao.create(s));

		// Create Copy of s
		ResourceAmount sCopy = new ResourceAmount();

		sCopy.setResource(s.getResource());

		sCopy.setAmount(s.getAmount());

		// Change Values of s

		s.setResource(null);

		s.setAmount(1);

		s = dao.update(s);
		// check if Update successful
		Assert.assertNotNull(s);

		Assert.assertTrue(s.getResource() == sCopy.getResource());

		Assert.assertTrue(s.getAmount() != sCopy.getAmount());

	}

	@Test
	public void deletingTest() throws DAOException {
		Assert.assertTrue(dao.create(entity));
		Assert.assertTrue(dao.delete(entity.getId()));
		ResourceAmount s = dao.get(entity.getId());
		Assert.assertNull(s);
		entity = null;
	}
}
