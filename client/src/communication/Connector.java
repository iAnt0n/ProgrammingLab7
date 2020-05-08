package communication;

import utils.UserInterface;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;

/**
 * Класс, отвечающий за соединение и связь с сервером
 */
public class Connector {
    private Socket socket;
    private OutputStream out;
    private BufferedReader in;
    public static boolean retainsConnection;

    /**
     * Конструктор, создающий новый сокет и получающий связанные с ним потоки
     * @param host адрес хоста сервера
     * @param port номер порта
     * @throws IOException при ошибке получения потоков, связанных с сокетом
     */
    private Connector(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = socket.getOutputStream();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        retainsConnection=true;
    }

    public BufferedReader getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public static Connector connectToServ(String host, int port, UserInterface ui) {
        LocalTime first = LocalTime.now();
        while (!retainsConnection) {
            try {
                return new Connector(host, port);
            } catch (IOException e) {
                LocalTime second = LocalTime.now();
                if (second.getSecond() - first.getSecond() > 10) {
                    ui.writeln("Соединение с сервером не может быть восстановлено, простите");
                    System.exit(1);
                }
            }
        }
        return null;
    }

    public void sendTO(TransferObject TO, UserInterface ui) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(TO);
            oos.flush();
            out.write(baos.toByteArray());
        }
    }
}
