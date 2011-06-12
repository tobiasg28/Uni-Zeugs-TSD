package frontend;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import entities.GameStep;

import backend.BackendLocator;
import notification.Backend;
import notification.Frontend;

public class BackendConnection {
	static private Backend backend= null;
	static private Frontend frontend = null;
	static private Logger logger = Logger.getLogger(BackendConnection.class);
	static {
		try {
			frontend = new FrontendImpl();
			backend = BackendLocator.getBackendByHost(null);
			backend.registerClient(frontend);
			System.out.println("RMI registred");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public Backend getBackend(){
		return backend;
	}
	
}
