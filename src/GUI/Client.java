package GUI;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class Client extends Frame implements ActionListener, WindowListener {

    TextField tf, tf1, tf2, tf3, tf4; // Добавлено поле tf4 для ввода арифметического действия
    TextArea ta;
    Label la, la1, la2, la3, la4; // Добавлено поле la4 для метки арифметического действия
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;

    public static void main(String args[]) {
        Client c = new Client();
        c.GUI();
    }

    private void GUI() {
        setTitle("КЛИЕНТ");

        tf = new TextField("127.0.0.1"); // IP-адрес клиента
        tf1 = new TextField("1024"); // Порт клиента
        tf2 = new TextField();
        tf3 = new TextField();
        tf4 = new TextField(); // Добавлено поле для ввода арифметического действия
        ta = new TextArea();
        la = new Label("IP ADRESS");
        la1 = new Label("port");
        la2 = new Label("Number 1");
        la3 = new Label("Number 2");
        la4 = new Label("Operation"); // Добавлена метка для арифметического действия

        Button btn = new Button("Connect");
        Button btn1 = new Button("Send");

        tf.setBounds(200, 50, 70, 25);
        tf1.setBounds(330, 50, 70, 25);
        tf2.setBounds(200, 100, 70, 25);
        tf3.setBounds(200, 150, 70, 25);
        tf4.setBounds(200, 200, 70, 25); // Поле для ввода арифметического действия
        ta.setBounds(150, 250, 300, 200);
        btn.setBounds(50, 50, 70, 25);
        btn1.setBounds(50, 250, 70, 25);
        la.setBounds(130, 50, 150, 25);
        la1.setBounds(280, 50, 150, 25);
        la2.setBounds(130, 100, 150, 25);
        la3.setBounds(130, 150, 150, 25);
        la4.setBounds(130, 200, 150, 25); // Метка для арифметического действия

        add(tf);
        add(tf1);
        add(tf2);
        add(tf3);
        add(tf4); // Добавлено поле для ввода арифметического действия
        add(btn);
        add(btn1);
        add(ta);
        add(la);
        add(la1);
        add(la2);
        add(la3);
        add(la4); // Добавлена метка для арифметического действия

        setSize(600, 600);
        setVisible(true);
        addWindowListener(this);

        btn.addActionListener(al);
        btn1.addActionListener(this);
    }

    public void windowClosing(WindowEvent we) {
        if (sock != null && !sock.isClosed()) {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dispose();
    }

    public void windowActivated(WindowEvent we) {}

    public void windowClosed(WindowEvent we) {}

    public void windowDeactivated(WindowEvent we) {}

    public void windowDeiconified(WindowEvent we) {}

    public void windowIconified(WindowEvent we) {}

    public void windowOpened(WindowEvent we) {}

    public void actionPerformed(ActionEvent e) {
        if (sock == null) {
            return;
        }

        try {
            is = sock.getInputStream();
            os= sock.getOutputStream();

            String number1 = tf2.getText();
            String number2 = tf3.getText();
            String operation = tf4.getText(); // Получение введенного арифметического действия

            String data = number1 + " " + number2 + " " + operation;

            os.write(data.getBytes());

            byte[] bytes = new byte[1024];
            is.read(bytes);

            String str = new String(bytes, "UTF-8");

            ta.setText(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
                sock.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                sock = new Socket(tf.getText(), Integer.parseInt(tf1.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}