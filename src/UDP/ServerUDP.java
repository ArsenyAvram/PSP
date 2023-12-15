package UDP;

import java.net.*;
import java.io.*;
import static java.lang.Math.*;

public class ServerUDP {
    public final static int DEFAULT_PORT = 8001;

    public void runServer() throws IOException {
        DatagramSocket socket = null;
        try {
            boolean stopFlag = false;
            byte[] buf = new byte[512];
            socket = new DatagramSocket(DEFAULT_PORT);
            System.out.println("UDPServer: Started on " + socket.getLocalAddress() + ":" + socket.getLocalPort());

            while (!stopFlag) {
                DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
                socket.receive(receivePacket);
                String numbers = new String(receivePacket.getData()).trim();

                // Обработка полученных чисел
                String[] numberArray = numbers.split(",");
                int x= Integer.parseInt(numberArray[0]);
                int y = Integer.parseInt(numberArray[1]);
                int z = Integer.parseInt(numberArray[2]);

                double result = log(pow(y, -sqrt(abs(x)))) * (x - y / 2) + pow(sin(atan(z)), 2) + exp(x + y);

                // Отправка ответа клиенту
                String response = "Result: " + result;
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }

            System.out.println("UDPServer: Stopped");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerUDP server = new ServerUDP();
            server.runServer();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
