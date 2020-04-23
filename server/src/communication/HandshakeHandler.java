package communication;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class HandshakeHandler {
    public static void acceptConnection(ServerSocketChannel ssc, Selector selector) throws IOException {
        Logger log = Logger.getLogger(HandshakeHandler.class.getName());
        SocketChannel channel = ssc.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        log.info("Установлено соединение с клиентом "+channel.getRemoteAddress());
    }
}
