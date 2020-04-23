package communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ClientHandler {
    public String response;

    public ClientHandler(int port) throws IOException {
        Logger log = Logger.getLogger(ClientHandler.class.getName());
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        server.socket().bind(address);
        log.info("Сервер запущен: IP "+ InetAddress.getLocalHost().getHostAddress() +" Port "+port);
    }
}
