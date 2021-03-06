package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import interfaces.ChatProxy;
import interfaces.ChatServer;
import interfaces.ClientProxy;

public class SimpleClientProxy implements ClientProxy{
	
	private String username;
	private ChatProxy chat;
	
	public SimpleClientProxy(){
		username = "anon";
	}
	
	public SimpleClientProxy(String username){
		this.username = username;
	}

	@Override
	public void recieveMessage(String username, String message) throws RemoteException {
	    System.out.println((username.equals(this.username) ? ">> " : "<< ") +username+": "+message);
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
            String s = "";
            Scanner sc = new Scanner(System.in);
		    boolean done = false;
		    while(!done) {
                System.out.println("Enter your Username:");
                s = sc.nextLine();
                if(!s.equalsIgnoreCase("")) {
                    done = true;
                } else {
                    System.out.println("You cannot name yourself '" +s + "'!");
                }
            }

            ClientProxy prox1 = new SimpleClientProxy(s);
            ClientProxy stub1 = (ClientProxy) UnicastRemoteObject.exportObject(prox1, 0);

            Registry registry = LocateRegistry.getRegistry();
            ChatServer server = (ChatServer) registry.lookup("ChatServer");

            stub1.setChatProxy(server.subscribeUser(stub1));

            System.out.println("Started the chat and connected to the server! You are now able to chat:\n");

            s = "";
		    while(true) {
                s = sc.nextLine();
                if(!s.equalsIgnoreCase("")) {
                    stub1.sendMessage(s);
                }
            }

			// EXAMPLE:
			/*ClientProxy prox1 = new SimpleClientProxy("UserA");
			ClientProxy stub1 = (ClientProxy) UnicastRemoteObject.exportObject(prox1, 0);

			ClientProxy prox2 = new SimpleClientProxy("Hklkjjigdu");
			ClientProxy stub2 = (ClientProxy) UnicastRemoteObject.exportObject(prox2, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			ChatServer server = (ChatServer) registry.lookup("ChatServer");
			
			stub1.setChatProxy(server.subscribeUser(stub1));
			stub2.setChatProxy(server.subscribeUser(stub2));
			
			int DELAY = 1000;
			
			stub1.sendMessage("Hallo anderer Typ");
			Thread.sleep(DELAY);
			
			stub2.sendMessage("HELO");
			Thread.sleep(DELAY);
			
			stub2.sendMessage("Sch�nes Wetter haben wir, nicht?");
			Thread.sleep(DELAY);
			
			stub1.sendMessage("Jo ultra nice man");
			Thread.sleep(DELAY);
			
			stub2.sendMessage("Jooo");
			Thread.sleep(DELAY);
			
			stub1.sendMessage("!ps -demon hearing test");
			Thread.sleep(DELAY);*/
			
			
		} catch (RemoteException | NotBoundException e) {
			System.out.println("An exception occurred while starting the client, printing StackTrace...\n\n");
			e.printStackTrace();
		}
	}
}
