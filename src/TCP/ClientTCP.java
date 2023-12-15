package TCP;
import java.io.*;
import java.net.*;

public class ClientTCP {
    public static void main(String[] arg) {
        try {
            System.out.println("Соединение с сервером...");
            Socket clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("Соединение установлено.");

            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());

            String clientMessage;
            do {
                System.out.print("Введите операцию (+, -, /, *): ");
                String operation = stdin.readLine();
                System.out.print("Введите первое число: ");
                double operand1 = Double.parseDouble(stdin.readLine());
                System.out.print("Введите второе число: ");
                double operand2 = Double.parseDouble(stdin.readLine());
                clientMessage = operation + "," + operand1 + "," + operand2;


                System.out.println("Вы ввели: " + clientMessage);
                coos.writeObject(clientMessage);
                System.out.println("~server~: " + cois.readObject());
                System.out.println("---------------------------");
            } while (!clientMessage.equals("qwerty"));

            coos.close();
            cois.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}