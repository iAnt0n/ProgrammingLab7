package communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

public class ClientHandler {
    private ServerSocketChannel server;
    private Selector selector;

    public ClientHandler(int port) throws IOException {
        Logger log = Logger.getLogger(ClientHandler.class.getName());
        this.server = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        server.socket().bind(address);
        server.configureBlocking(false);
        this.selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("Сервер запущен: IP "+ InetAddress.getLocalHost().getHostAddress() +" Port "+port);
    }

    public Selector getSelector() {
        return selector;
    }

    public void acceptConnection() throws IOException {
        HandshakeHandler.acceptConnection(server, selector);
    }

    public void closeServer() {
        try {
            server.close();
        }
        catch (IOException ignored){}
    }
}