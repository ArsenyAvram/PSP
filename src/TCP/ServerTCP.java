package TCP;
import java.io.*;
import java.net.*;

public class ServerTCP {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        ObjectInputStream cois = null;
        ObjectOutputStream coos = null;

        try {
            System.out.println("Сервер запущен. Ожидание подключения...");
            serverSocket = new ServerSocket(2525);
            clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен.");

            cois = new ObjectInputStream(clientSocket.getInputStream());
            coos = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                String clientMessage = (String) cois.readObject();
                System.out.println("Получено сообщение от клиента: " + clientMessage);

                if (clientMessage.equals("qwerty")) {
                    System.out.println("Клиент отключился.");
                    break;
                }

                String[] parts = clientMessage.split(",");
                String operation = parts[0];
                double operand1 = Double.parseDouble(parts[1]);
                double operand2 = Double.parseDouble(parts[2]);
                double result;

                switch (operation) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand1 / operand2;
                        break;
                    default:
                        result = 0;
                        break;
                }

                coos.writeObject("Результат: " + result);
                coos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cois != null) cois.close();
                if (coos != null) coos.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}