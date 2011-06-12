/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import entities.GameMap;
import entities.GameStep;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOImpl;

/**
 * 
 * @author wi3s3r
 */
class SingleGameStepUpdate implements Runnable{

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
	}

	public void setCurrentGameStep(GameStep currentGameStep) {
		this.currentGameStep = currentGameStep;
	}

	public void stopRunning() {
		running = false;
	}

	@Override
	public void run() {
		GameMap currentGameMap=null;
		while (running) {
			if (currentGameStep != null
					&& currentGameStep.getDate().getTime()!=calculatedGameStep) {
				try {
					DAOImpl.getInstance().getEntityManager().getTransaction().begin();
					if(currentGameMap==null){
						currentGameMap = DAOImpl.getInstance().getEntityManager().find(GameMap.class, this.gameMapId);
					}else{
						DAOImpl.getInstance().getEntityManager().refresh(currentGameMap);
					}GameMap nextGameMap = GameStateUpdater.nextState(
							currentGameMap, currentGameStep);
					DAOImpl.getInstance().getEntityManager().merge(nextGameMap);
					DAOImpl.getInstance().getEntityManager().getTransaction().commit();
					calculatedGameStep=currentGameStep.getDate().getTime();
				} catch (Exception ex) {
					DAOImpl.getInstance().getEntityManager().getTransaction().rollback();
					currentGameMap = null;
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
