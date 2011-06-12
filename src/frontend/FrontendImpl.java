package frontend;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import entities.GameStep;


public class FrontendImpl implements notification.Frontend, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private Logger logger = Logger.getLogger(FrontendImpl.class);
	@Override
	public void onGameStepCreated(GameStep gameStep) throws RemoteException {
		logger.debug("Frontend: Gamestep " + gameStep.getId() + " recieved");
		ActualGameStep.setGameStep(gameStep);
		System.out.println("gamestep set");
	}

}
