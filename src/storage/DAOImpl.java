package storage;

import java.util.List;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DAOImpl{

    private static Logger logger = Logger.getLogger(DAOImpl.class);
    /**
     * Pattern: singleton
     */
    private static DAOImpl inst;

    public static DAOImpl getInstance() {
        if (inst == null) {
            return (inst = new DAOImpl());
        }
        return inst;
    }
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private DAOImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("dao");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public <T> boolean create(T object) throws DAOException {
        logger.debug("Request: Create " + object.toString());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
            logger.debug(object.toString() + " created.");
            return true;
        } catch (Exception e) {
            logger.debug("DAOException during creating of " + object.toString());
            throw new DAOException(e);
        }
    }

    public <T> T get(Object id, Class<T> c) throws DAOException {
        logger.debug("Request: Get " + id.toString());
        try {
            T found = entityManager.find(c, id);
            if (found == null) {
                logger.debug("Object with id " + id.toString() + " not found.");
                return null;
            }
            logger.debug(found.toString() + " found.");
            return found;
        } catch (Exception e) {
            logger.debug("DAOException during reading of object with id " + id.toString());
            throw new DAOException(e);
        }
    }
    
    public <T> List<T> getAll(Class<T> c) throws DAOException {
        logger.debug("Request: GetAll");
        try {
            Query query = entityManager.createQuery("SELECT o FROM " + "swag_" + c.getName(), c);
            List<T> res = query.getResultList();
            return res;
        } catch (Exception e) {
            logger.debug("DAOException during getting of " + c.getName());
            throw new DAOException(e);
        }
    }

    public <T> T update(T object) throws DAOException {
        logger.debug("Request: Update " + object.toString());
        try {
            entityManager.getTransaction().begin();
            T updated = entityManager.merge(object);
            entityManager.getTransaction().commit();
            logger.debug(updated.toString() + " updated.");
            return updated;
        } catch (Exception e) {
            logger.debug("DAOException during updating of " + object.toString());
            throw new DAOException(e);
        }
    }

    public <T> boolean delete(Object id, Class<T> c) throws DAOException {
        Object object = null;
        logger.debug("Request: Delete " + id.toString());
        try {
            object = entityManager.find(c, id);
            entityManager.getTransaction().begin();
            entityManager.remove(object);
            entityManager.getTransaction().commit();
            logger.debug(object.toString() + " deleted.");
            return true;
        } catch (Exception e) {
            logger.debug("DAOException during deleting of " + object.toString());
            throw new DAOException(e);
        }
    }
    
    public EntityManager getEntityManager() {
    	return entityManager;
    }
}
