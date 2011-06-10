package frontend;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import backend.BackendLocator;
import notification.Backend;
import notification.Frontend;

public class BackendConnection {
	static Backend backend= null;
	static Frontend frontend = null;
	static Logger logger = Logger.getLogger(BackendConnection.class);
	static {
		try {
			backend = BackendLocator.getBackendByHost(null);
			frontend = new FrontendImpl();
			backend.registerClient(frontend);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static Backend getBackend(){
		return backend;
	}
}
