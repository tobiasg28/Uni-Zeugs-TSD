package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import storage.DAOException;
import storage.DAOImpl;
import dao.GameMapDAO;
import dao.MessageDAO;
import dao.ParticipationDAO;
import dao.ResourceDAO;
import dao.UserDAO;
import dao.UserDAOExtended;
import entities.Base;
import entities.GameMap;
import entities.Message;
import entities.Participation;
import entities.Resource;
import entities.ResourceAmount;
import entities.Troop;
import entities.User;

public class UserTestExtended {
	private UserDAOExtended dao;
	private MessageDAO messageDao;
	private User entity;
	private ParticipationDAO partiDao;
	private GameMapDAO mapDao;
	private ResourceDAO resourceDao;

	@Before
	public void setUp() throws DAOException {
		dao = new UserDAOExtended();
		messageDao = new MessageDAO();
		partiDao = new ParticipationDAO();
		mapDao = new GameMapDAO();
		resourceDao = new ResourceDAO();
		entity = new User();
	}
	
	@Test
	public void loginTest() throws DAOException {
		entity = new User();
		entity.setUsername("tobi");
		entity.setPassword("tobiii");
		entity.setFullName("BLA");
		entity.setAdress("BLA");
		entity.setTimezone(java.util.TimeZone.getTimeZone("GMT"));
		entity.setOutMessages(new ArrayList<Message>());
		entity.setInMessages(new ArrayList<Message>());
		entity.setParticipations(new ArrayList<Participation>());

		Assert.assertTrue(dao.create(entity));
		Assert.assertNotNull(entity.getId());
		
		Assert.assertNull(dao.login("nobody", "tobiii"));
		Assert.assertNotNull(dao.login("tobi", "tobiii"));
	}
	
	@Test
	public void deleteTest() throws DAOException {
		User user = new User();
		user.setUsername("tobi2");
		user.setPassword("tobiii");
		
		User user2 = new User();
		user2.setUsername("harry");
		user2.setPassword("harrrrry");
		
		Assert.assertTrue(dao.create(user));
		Assert.assertTrue(dao.create(user2));
		
		List<Message> inMessages = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setFromUser(user2);
		message1.setToUser(user);
		message1.setTitle("MessageTitle4");
		inMessages.add(message1);
		user.setInMessages(inMessages);
		
		List<Message> outMessages = new ArrayList<Message>();
		Message message2 = new Message();
		message2.setFromUser(user);
		message2.setToUser(user2);
		message2.setTitle("MessageTitle24");
		outMessages.add(message2);
		user.setOutMessages(outMessages);
		
		flush();
		
		Assert.assertNotNull(messageDao.get(message1.getId()));
		
		Participation parti1 = new Participation();
		
		user.getParticipations().add(parti1);
		
		Base base1 = new Base();
		parti1.getBases().add(base1);
		
		Troop troop1 = new Troop();
		parti1.getTroops().add(troop1);
		
		Resource res = new Resource();
		res.setName("Flower");
		resourceDao.create(res);
		
		ResourceAmount resAm = new ResourceAmount();
		resAm.setAmount(5);
		resAm.setResource(res);
		parti1.getResources().add(resAm);
		
		flush();
		
		Assert.assertTrue(dao.delete(user.getId()));
		
		Assert.assertNull(messageDao.get(message1.getId()));
		
	}
	
	private void flush() {
		try {
			DAOImpl.getInstance().getEntityManager().getTransaction().begin();
			DAOImpl.getInstance().getEntityManager().flush();
			DAOImpl.getInstance().getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
