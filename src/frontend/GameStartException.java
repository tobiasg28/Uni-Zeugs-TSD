/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;


/**
 *
 * @author wi3s3r
 */
public class GameStartException extends Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3677764933102266726L;


	public GameStartException(String msg) {
        super(msg); 
    }
    

    public GameStartException(String msg, Throwable t) {
        super(msg,t);
    }
    
}
