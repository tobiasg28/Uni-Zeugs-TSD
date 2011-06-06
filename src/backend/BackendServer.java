package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import swag.Constants;

import notification.Backend;

/**
 * SWAG Backend Server Runner
 * 
 * This server implementation creates a new NotificationServer instance
 * and exports it using RMI with the well-known RMI object name, so other
 * frontends can find the backend and register for updates and/or request
 * updates for new game map IDs.
 * 
 * @author thp
 **/
public class BackendServer {
	private static Logger logger = Logger.getLogger(NotificationServer.class);

	public static void main(String [] args) {
		NotificationServer server = new NotificationServer();
		try {
			logger.info("Registering NotificationServer as '" + Constants.BACKEND_RMI_NAME + "'...");
			Backend backend = (Backend)UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(Constants.BACKEND_RMI_PORT);
			registry.bind(Constants.BACKEND_RMI_NAME, backend);
			logger.info("Backend server up and running. Press Enter to shutdown.");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			br.readLine();
			
			logger.info("Shutting down, please wait...");
			UnicastRemoteObject.unexportObject(server, false);
			registry.unbind(Constants.BACKEND_RMI_NAME);
			logger.info("Shutdown complete.");
		} catch (RemoteException e) {
			logger.error("There was a problem exporting this object.");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			logger.error("A backend is already running on this machine!");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error reading from standard input (EOF?)");
			e.printStackTrace();
		} catch (NotBoundException e) {
			logger.error("Backend wasn't bound; couldn't unbind.");
			e.printStackTrace();
		}
	}
}
