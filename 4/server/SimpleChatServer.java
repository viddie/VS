package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import interfaces.ChatProxy;
import interfaces.ChatServer;
import interfaces.ClientProxy;

public class SimpleChatServer implements ChatServer{
	
	private ArrayList<ClientProxy> connections = new ArrayList<>();
	private ChatProxy chat;
	
	
	public SimpleChatServer() throws RemoteException {
		ChatProxy someProx = new SimpleChatProxy(this);
		chat = (ChatProxy) UnicastRemoteObject.exportObject(someProx, 0);
	}
	

	@Override
	public ChatProxy subscribeUser(ClientProxy handle) throws RemoteException {
		connections.add(handle);
		return chat;
	}

	@Override
	public boolean unsubscribeUser(ClientProxy handle) throws RemoteException {
		return connections.remove(handle);
	}

	@Override
	public void broadcastMessage(String username, String message) throws RemoteException {
		for(ClientProxy prox : connections){
			prox.recieveMessage(username, message);
		}
	}
	
	
	public static void main(String[] args){
		try{
			ChatServer chatServer = new SimpleChatServer();
			
			ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(chatServer, 0);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("ChatServer", stub);
			
			System.out.println("Server ready");
					
		}catch(RemoteException e){
			System.out.println("RemoteException occurred while starting the server, printing StackTrace...\n\n");
			e.printStackTrace();
		}
		
		
	}
}
