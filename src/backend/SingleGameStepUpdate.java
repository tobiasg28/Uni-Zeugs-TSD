/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import dao.GameMapDAO;
import entities.GameMap;
import entities.GameStep;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOException;

/**
 * 
 * @author wi3s3r
 */
class SingleGameStepUpdate implements Runnable {

	private GameMapDAO gameMapDAO;
	private NotificationServer notificationServer;

	private Long gameMapId;
	private GameStep currentGameStep;
	private long calculatedGameStep;

	private boolean running;

	public SingleGameStepUpdate(NotificationServer notificationServer,
			long gameMapId) {
		this.notificationServer = notificationServer;
		this.gameMapId = gameMapId;
		this.running = true;
		this.gameMapDAO = new GameMapDAO();
	}

	public void setCurrentGameStep(GameStep currentGameStep) {
		this.currentGameStep = currentGameStep;
	}

	public void stopRunning() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			if (currentGameStep != null
					&& currentGameStep.getDate().getTime()!=calculatedGameStep) {
				try {
					/*GameMap currentGameMap = gameMapDAO.get(gameMapId);
					GameMap nextGameMap = GameStateUpdater.nextState(
							currentGameMap, currentGameStep);
					gameMapDAO.update(nextGameMap);*/
					GameStateUpdater.nextState(
							gameMapId, currentGameStep);
					calculatedGameStep=currentGameStep.getDate().getTime();
				} catch (Exception ex) {
					Logger.getLogger(SingleGameStepUpdate.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				
				/* Notify the frontends that a new GameStep is available */
				notificationServer.onGameStepCreated(currentGameStep);
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
