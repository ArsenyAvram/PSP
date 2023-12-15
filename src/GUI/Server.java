package GUI;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int countClients = 0;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1024);
            System.out.println("Сервер запущен. Ожидание подключения клиентов...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                countClients++;
                System.out.println("=======================================");
                System.out.println("Клиент " + countClients + " подключен");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private InputStream is;
        private OutputStream os;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                is = clientSocket.getInputStream();
                os = clientSocket.getOutputStream();

                byte[] bytes = new byte[1024];
                int bytesRead;
                boolean flag = true;

                while (flag) {
                    bytesRead = is.read(bytes);
                    String data = new String(bytes, 0, bytesRead, "UTF-8");
                    System.out.println("Получены данные от клиента: " + data);

                    String[] parts = data.split(" ");

                    if (parts.length != 3) {
                        flag = false;
                        break;
                    }

                    double number1 = Double.parseDouble(parts[0]);
                    double number2 = Double.parseDouble(parts[1]);
                    String operation = parts[2];
                    double result = 0.0;

                    switch (operation) {
                        case "+":
                            result = number1 + number2;
                            break;
                        case "-":
                            result = number1 - number2;
                            break;
                        case "*":
                            result = number1 * number2;
                            break;
                        case "/":
                            result = number1 / number2;
                            break;
                        default:
                            System.out.println("Неподдерживаемая операция: " + operation);
                            flag = false;
                            break;
                    }

                    String response = String.valueOf(result);
                    os.write(response.getBytes());
                    System.out.println("Результат отправлен клиенту: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null)
                        os.close();
                    if (is != null)
                        is.close();
                    if (clientSocket != null)
                        clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Клиент " + countClients + " отключен");
        }
    }
}