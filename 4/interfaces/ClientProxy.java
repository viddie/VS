package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientProxy extends Remote {
	public void recieveMessage(String username, String message) throws RemoteException;
	public void sendMessage(String message) throws RemoteException;
	public void setChatProxy(ChatProxy proxy);
}
