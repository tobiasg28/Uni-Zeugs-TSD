/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

/**
 *
 * @author wi3s3r
 */
public class AcceptanceException  extends Exception{
    
    public AcceptanceException(String msg) {
        super(msg); 
    }
    

    public AcceptanceException(String msg, Throwable t) {
        super(msg,t);
    }
    
    
}
