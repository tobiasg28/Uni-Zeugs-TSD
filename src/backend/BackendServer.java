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
		// Make sure we have the basic content put into the DB before beginning
		try {
			DatabaseInitializer.initializeDB();
		} catch (DatabaseInitializerException e1) {
			System.out.println("Could not initialize database!");
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		NotificationServer server = new NotificationServer();
		try {
			System.out.println("Registering NotificationServer as '" + Constants.BACKEND_RMI_NAME + "'...");
			Backend backend = (Backend)UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(Constants.BACKEND_RMI_PORT);
			registry.bind(Constants.BACKEND_RMI_NAME, backend);
			System.out.println("Backend server up and running. Press Enter to shutdown.");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			br.readLine();
			
			System.out.println("Shutting down, please wait...");
			UnicastRemoteObject.unexportObject(server, false);
			registry.unbind(Constants.BACKEND_RMI_NAME);
			System.out.println("Shutdown complete.");
		} catch (RemoteException e) {
			System.out.println("There was a problem exporting this object.");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("A backend is already running on this machine!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading from standard input (EOF?)");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("Backend wasn't bound; couldn't unbind.");
			e.printStackTrace();
		}
	}
}
