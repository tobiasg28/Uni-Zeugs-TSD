package backend;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import entities.GameStep;

import notification.Backend;
import notification.Frontend;

public class NotificationServer implements Backend {
	private List<Frontend> registeredFrontends;
	private Map<Long,PeriodicMapUpdater> mapUpdaters;
	
	private static Logger logger = Logger.getLogger(NotificationServer.class);
	
	public NotificationServer() {
		logger.debug("New NotificationServer created");
		registeredFrontends = new LinkedList<Frontend>();
		mapUpdaters = new HashMap<Long,PeriodicMapUpdater>();
	}
	
	public void onGameStepCreated(GameStep gameStep) {
		logger.debug("Notifying " + registeredFrontends.size() + " frontends...");
		for (Frontend frontend : registeredFrontends) {
			try {
				frontend.onGameStepCreated(gameStep);
			} catch (RemoteException e) {
				logger.error("Frontend doesn't respond: " + frontend);
			}
		}
	}

	@Override
	public void registerClient(Frontend frontend) throws RemoteException {
		registeredFrontends.add(frontend);
		logger.info("New frontend registered for updates: " + frontend);
	}

	@Override
	public void unregisterClient(Frontend frontend) throws RemoteException {
		if (registeredFrontends.contains(frontend)) {
			registeredFrontends.remove(frontend);
			logger.info("Frontend unregistered: " + frontend);
		} else {
			logger.warn("Frontend tried to unregister without being registered: " + frontend);
		}
	}

	@Override
	public void onMapCreated(long gameMapId) throws RemoteException {
		/* Create a separate thread for each map that is to be updated */
		PeriodicMapUpdater mapUpdater = new PeriodicMapUpdater(this, gameMapId);
		mapUpdaters.put(gameMapId, mapUpdater);
		
		logger.info("Starting new PeriodicMapUpdater for map " + gameMapId);
		
		/* Start updating the given map in the background */
		mapUpdater.start();
	}

	@Override
	public void onMapDestroyed(long gameMapId) throws RemoteException {
		/* Stop carrying out GameStep updates for the given map */
		if (mapUpdaters.containsKey(gameMapId)) {
			PeriodicMapUpdater mapUpdater = mapUpdaters.remove(gameMapId);
			
			logger.info("Stopping PeriodicMapUpdater for map " + gameMapId);
			
			/* Tell mapUpdater to stop running as soon as possible */
			mapUpdater.stopRunning();
		} else {
			logger.warn("Map destroyed without existing PeriodicMapUpdater: " + gameMapId);
		}
	}

}
