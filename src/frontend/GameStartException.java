/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import storage.DAOException;

/**
 *
 * @author wi3s3r
 */
public class GameStartException extends Exception{
    
    public GameStartException(String msg) {
        super(msg); 
    }
    

    public GameStartException(String msg, Throwable t) {
        super(msg,t);
    }
    
}
