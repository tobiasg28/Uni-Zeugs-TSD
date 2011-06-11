package frontend;

public class GamePlayException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6667337924151302531L;


	public GamePlayException(String msg) {
        super(msg); 
    }
    

    public GamePlayException(String msg, Throwable t) {
        super(msg,t);
    }

}
