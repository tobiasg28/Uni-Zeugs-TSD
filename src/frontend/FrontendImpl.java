package frontend;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import entities.GameStep;

public class FrontendImpl implements notification.Frontend{

	static private Logger logger = Logger.getLogger(FrontendImpl.class);
	@Override
	public void onGameStepCreated(GameStep gameStep) throws RemoteException {
		logger.debug("Frontend: Gamestep " + gameStep.getId() + " recieved");
	}

}
