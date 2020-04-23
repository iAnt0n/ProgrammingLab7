package communication;

import exceptions.ConnectionCancelledException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class RequestHandler {
    public static TransferObject readRequest(SocketChannel channel) throws IOException, ClassNotFoundException {
        Logger log = Logger.getLogger(RequestHandler.class.getName());
        ByteBuffer bb = ByteBuffer.allocate(5 * 1024);
        try {
            channel.read(bb);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bb.array()))) {
                TransferObject TO = (TransferObject) ois.readObject();
                log.info("Получены данные от клиента "+channel.getRemoteAddress());
                return TO;
            }
            catch (StreamCorruptedException e){
                SocketAddress clientAddr = channel.getRemoteAddress();
                channel.close();
                log.info("Разорвано соединение с клиентом "+clientAddr);
                throw new ConnectionCancelledException("Соединение разорвано");
            }
        }
        catch (IOException e){
            SocketAddress clientAddr = channel.getRemoteAddress();
            channel.close();
            log.info("Разорвано соединение с клиентом "+clientAddr);
            throw new ConnectionCancelledException("Соединение разорвано");
        }
    }
}
