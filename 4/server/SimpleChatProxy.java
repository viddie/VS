package server;

import java.rmi.RemoteException;

import interfaces.ChatProxy;
import interfaces.ChatServer;

public class SimpleChatProxy implements ChatProxy{
	
	private ChatServer parent;
	
	public SimpleChatProxy(ChatServer parent){
		this.parent = parent;
	}

	@Override
	public void sendMessage(String username, String message) throws RemoteException {
		parent.broadcastMessage(username, message);
	}
}
