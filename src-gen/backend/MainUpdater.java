/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import dao.GameMapDAO;
import dao.GameStepDAO;
import entities.GameMap;
import entities.GameStep;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOException;

/**
 *
 * @author wi3s3r
 */
class MainUpdater implements Runnable{
    
    private GameMapDAO gameMapDAO;
    
    private Long gameMapId;
    private GameStep currentGameStep;

    public MainUpdater(long gameMapId, GameStep currentGameStep) {
        this.gameMapId = gameMapId;
        this.currentGameStep = currentGameStep;
        
        this.gameMapDAO = new GameMapDAO();
    }

    @Override
    public void run() {
        try {
            GameMap currentGameMap = gameMapDAO.get(gameMapId);
            GameMap nextGameMap = GameState.nextState(currentGameMap, currentGameStep);
            gameMapDAO.update(nextGameMap);
        } catch (DAOException ex) {
            Logger.getLogger(MainUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TODO Notification
    }
    
}
