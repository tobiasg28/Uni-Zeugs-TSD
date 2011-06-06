package notification;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.GameStep;

public interface Frontend extends Remote {
	void onGameStepCreated(GameStep gameStep) throws RemoteException;
}
