package UDP;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ClientUDP{
    public void runClient() throws IOException {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            System.out.println("UDPClient: Started");

            // Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Ввод трех чисел с клавиатуры
            System.out.print("Enter the first number: ");
            int number1 = Integer.parseInt(reader.readLine());
            System.out.print("Enter the second number: ");
            int number2 = Integer.parseInt(reader.readLine());
            System.out.print("Enter the third number: ");
            int number3 = Integer.parseInt(reader.readLine());

            String numbers = number1 + "," + number2 + "," + number3;
            byte[] sendData = numbers.getBytes();
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 8001;
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Получение ответа от сервера
            byte[] receiveData = new byte[512];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData()).trim();
            System.out.println("UDPClient: Response from server: " + response);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP();
            client.runClient();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}