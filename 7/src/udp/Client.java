package udp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

class Client {
	private static int size=512;
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress ia = InetAddress.getByName("localhost");

		String s = new String("INITX;"+size+";demo.txt");
		byte[] raw = s.getBytes();
		
		// Senden der Initial-Message
		DatagramPacket packet = new DatagramPacket(raw, raw.length, ia, 8999);
		DatagramSocket dSocket = new DatagramSocket(8998);
		dSocket.send(packet);

		// Warten auf die Antworten und Kommunikation mit dem Server
		boolean eins = true;
		while (eins) {
			dSocket.receive(packet);
			// Erfolgsbestätigung oder Error erwartet
			String nachricht = new String(packet.getData());
			String parts[] = nachricht.split(";");
			if (parts[0].equals("ERROR")) {
				System.err.println(parts[1]);
				dSocket.close();
				System.exit(1);
			}
			eins = false;
		}
		// Holen der Daten
		FileOutputStream outFile = new FileOutputStream("demo2.txt");
		String prefix = "";
		do {
			s = "GET;";
			raw = s.getBytes();
			dSocket.send(new DatagramPacket(raw, raw.length, ia, 8999));
			eins=true;
			// Es wird solange auf die Packete gewartet, wie der PREFIX 'DATA' gültig ist
			while (eins) {
				dSocket.receive(packet);
				raw = new byte[size];
				raw=packet.getData();
				prefix = new String(raw).split(";")[0];
				if (prefix.equals("DATA")) {
					outFile.write(raw,5,raw.length-5);
					outFile.flush();
					outFile.close();
				}
				else {
					eins=false;
				}
			}
		} while (prefix.equals("DATA"));

	}

}