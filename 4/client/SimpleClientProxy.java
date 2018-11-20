package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.ChatProxy;
import interfaces.ChatServer;
import interfaces.ClientProxy;

public class SimpleClientProxy implements ClientProxy{
	
	private static int ID = 0;
	
	private String username;
	private ChatProxy chat;
	
	public SimpleClientProxy(){
		username = "name" + (ID++);
	}

	@Override
	public void recieveMessage(String username, String message) throws RemoteException {
		System.out.println("Message recieved - "+username+" says: "+message);
	}

	@Override
	public void sendMessage(String message) throws RemoteException{
		chat.sendMessage(username, message);
	}

	@Override
	public void setChatProxy(ChatProxy proxy){
		chat = proxy;
	}
	
	
	public static void main(String[] args){
		try {
			SimpleClientProxy prox = new SimpleClientProxy();
			
			Registry registry = LocateRegistry.getRegistry();
			ChatServer server = (ChatServer) registry.lookup("ChatServer");
			prox.setChatProxy(server.subscribeUser(prox));
			
			prox.sendMessage("Tohle nachricht");
			Thread.sleep(3 * 1000);
			prox.sendMessage("Tohle nachricht2");
			Thread.sleep(10 * 1000);
			
		} catch (RemoteException | NotBoundException | InterruptedException e) {
			System.out.println("An exception occurred while starting the client, printing StackTrace...\n\n");
			e.printStackTrace();
		}
	}
}
