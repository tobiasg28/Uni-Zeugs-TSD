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

/**
 *
 * @author wi3s3r
 */
public class Launcher {
    
    //time of GameStep
    private long sleeptime;
    
    private GameStepDAO gameStepDAO;

    public Launcher(long sleeptime) {
        this.sleeptime = sleeptime;
        this.gameStepDAO = new GameStepDAO();
    }
    
    /**
     * After the sleeptime starts the updating of the game and updates the GameStep
     * @param gameMapId
     * @throws InterruptedException 
     */
    public void launch(long gameMapId) throws InterruptedException{
        Thread updater;
        
        //A new game is started
        GameStep current = new GameStep();
        current.setDate(new Date(new Date().getTime() + sleeptime));
        try {
            gameStepDAO.create(current);
        } catch (DAOException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //The game is running
        while(true){
            try {
                Thread.sleep(sleeptime);
                
                //Current GameStep
                current = gameStepDAO.get(current.getId());
                
                //Updating of the game states
                updater = new Thread(new MainUpdater(gameMapId, current));
                updater.start();
                
                //Next GameStep
                current.setDate(new Date(new Date().getTime() + sleeptime));
                gameStepDAO.update(current);
            } catch (DAOException ex) {
                Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
