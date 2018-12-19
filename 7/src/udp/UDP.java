package udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;

public class UDP {
	static byte[] dataprefix = "DATA;".getBytes();

	public static void main(String[] args) throws IOException {
		int size = 0;
		File file = null;
		DatagramSocket socket = new DatagramSocket(8999);

		while (true) {
			// Auf Anfrage warten
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			socket.receive(packet);

			// Empfänger auslesen
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			int len = packet.getLength();
			byte[] data = packet.getData();

			String init = new String(data);
			String[] parts = init.split(";");
			// Anfrage des Clients wird geprüft
			if (!parts[0].equals("INITX")) {
				// Behandlung falls GET-Anfrage --> Daten in Datei werden mit Prefix gesendet
				if (parts[0].equals("GET")) {
					data = new byte[size];
					FileInputStream fileIn = new FileInputStream(file);
					int prefixsize = "DATA;".getBytes().length;
					do {
						data = new byte[size];
						fileIn.read(data, prefixsize, size - prefixsize);
						System.arraycopy(dataprefix, 0, data, 0, dataprefix.length);
						socket.send(new DatagramPacket(data, data.length, address, 8998));
					} while (fileIn.available() > 0);
					fileIn.close();
					socket.close();
					System.exit(1);
				} else {
					// Behandlung, falls keine INIT-Anfrage erhalten
					byte[] error = new String("ERROR;INITX erwartet").getBytes();
					socket.send(new DatagramPacket(error, len, address, port));
					socket.close();
					System.err.println("Crash!");
					System.exit(1);
				}
			}
			// Behandlung der INIT-Anfrage
			String[] daten = parts;
			size = Integer.parseInt(daten[1]);
			System.out.println(new String(data));
			file = new File(daten[2].trim());
			file.exists();
			System.out.println(file.getAbsolutePath());

			byte[] message = new String("OK").getBytes();
			socket.send(new DatagramPacket(message, message.length, address, 8998));
		}
	}

}