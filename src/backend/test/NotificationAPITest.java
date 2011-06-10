package backend.test;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import notification.Backend;
import notification.Frontend;

import org.apache.log4j.Logger;

import storage.DAOException;
import swag.Constants;

import dao.GameMapDAO;

import backend.BackendLocator;

import entities.GameMap;
import entities.GameStep;
import entities.Participation;
import entities.Square;

public class NotificationAPITest extends UnicastRemoteObject implements Frontend {
	protected NotificationAPITest() throws RemoteException {
		super();
	}
	
	public long createMap() throws DAOException {
		GameMapDAO dao = new GameMapDAO();
		GameMap map = new GameMap();
		map.setMaxUsers(10);
		map.setName("SuperMap");
		map.setParticipations(new LinkedList<Participation>());
		map.setSquares(new LinkedList<Square>());
		dao.create(map);
		return map.getId();
	}

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(NotificationAPITest.class);

	@Override
	public void onGameStepCreated(GameStep gameStep) throws RemoteException {
		logger.info("onGameStepCreated received: " + gameStep);
	}

	public static void main(String [] args) {
		long mapId;
		Backend backend = null;
		NotificationAPITest frontend = null;
		/* get backend for local host */
		try {
			logger.info("Creating new frontend");
			frontend = new NotificationAPITest();
			mapId = frontend.createMap();
			logger.info("Retrieving backend");
			backend = BackendLocator.getBackendByHost(null);
			logger.info("Registering with backend");
			backend.registerClient(frontend);
			logger.info("Requesting update for Map " + mapId);
			backend.onMapCreated(mapId);
			logger.info("Sleeping a bit (gamestep duration + 5 seconds)...");
			Thread.sleep(5000+Constants.GAMESTEP_DURATION_MS);
			logger.info("Unregistering the Map with ID " + mapId);
			backend.onMapDestroyed(mapId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (backend != null && frontend != null) {
				logger.info("Unregistering from backend");
				try {
					backend.unregisterClient(frontend);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					UnicastRemoteObject.unexportObject(frontend, false);
				} catch (NoSuchObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("Done!");
			frontend = null;
		}
	}

}
