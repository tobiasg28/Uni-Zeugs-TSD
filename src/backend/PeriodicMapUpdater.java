/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import dao.GameStepDAO;
import entities.GameStep;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOException;
import swag.Constants;

/**
 *
 * @author wi3s3r
 */
public class PeriodicMapUpdater extends Thread {
	private NotificationServer notificationServer;
    private GameStepDAO gameStepDAO;

    private long gameMapId;
    private boolean running;

    public PeriodicMapUpdater(NotificationServer notificationServer, long gameMapId) {
        this.notificationServer = notificationServer;
        this.gameStepDAO = new GameStepDAO();
        this.gameMapId = gameMapId;
        this.running = true;
    }
    
    public void stopRunning() {
    	running = false;
    }
    
    public void run() {
        Thread updater;
        
        //A new game is started
        GameStep current = new GameStep();
        current.setDate(new Date(new Date().getTime() + Constants.GAMESTEP_DURATION_MS));
        try {
            gameStepDAO.create(current);
        } catch (DAOException ex) {
            Logger.getLogger(PeriodicMapUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //The game is running
        while(running) {
            try {
                try {
					Thread.sleep(Constants.GAMESTEP_DURATION_MS);
				} catch (InterruptedException e) {
					// TODO: Calculate remaining time, sleep again
				}
				
				if (!running) break;
                
                //Current GameStep
                current = gameStepDAO.get(current.getId());
                
                //Updating of the game states
                updater = new Thread(new SingleGameStepUpdate(notificationServer, gameMapId, current));
                updater.start();
                
                //Next GameStep
                current.setDate(new Date(new Date().getTime() + Constants.GAMESTEP_DURATION_MS));
                gameStepDAO.update(current);
            } catch (DAOException ex) {
                Logger.getLogger(PeriodicMapUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
