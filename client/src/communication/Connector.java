package communication;

import java.io.*;
;
import java.net.Socket;

/**
 * Класс, устанавливающий соединение с сервером
 */
public class Connector {
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    /**
     * Конструктор, создающий новый сокет и получающий связанные с ним потоки
     * @param host адрес хоста сервера
     * @param port номер порта
     * @throws IOException при ошибке получения потоков, связанных с сокетом
     */
    public Connector(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public static Connector connect(String host, int port) throws IOException {
        return new Connector(host, port);
    }

}
