package backend;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import notification.Backend;

import swag.Constants;

public class BackendLocator {
	/**
	 * Get the backend instance running on the specified host.
	 **/
	public static Backend getBackendByHost(String host) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, Constants.BACKEND_RMI_PORT);
		Backend backend = (Backend)registry.lookup(Constants.BACKEND_RMI_NAME);
		return backend;
	}
}
