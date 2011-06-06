package notification;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Backend extends Remote {
	/* Registration of frontends to receive GameStep notifications */
	public void registerClient(Frontend frontend) throws RemoteException;
	public void unregisterClient(Frontend frontend) throws RemoteException;
	
	/* Handling of requests from frontends to handle game maps */
	public void onMapCreated(long gameMapId) throws RemoteException;
	public void onMapDestroyed(long gameMapId) throws RemoteException;
}
