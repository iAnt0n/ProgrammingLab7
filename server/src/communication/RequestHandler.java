package communication;

import exceptions.ConnectionCancelledException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class RequestHandler implements Callable<TransferObject> {
    private SocketChannel channel;
    private Logger log = Logger.getLogger(RequestHandler.class.getName());

    public RequestHandler(SocketChannel channel){
        this.channel=channel;
    }


    @Override
    public TransferObject call() {
        ByteBuffer bb = ByteBuffer.allocate(5 * 1024);
        try {
            channel.read(bb);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bb.array()))) {
                return (TransferObject) ois.readObject();
                //log.info("Получены данные от клиента "+channel.getRemoteAddress());
            }
        }
        catch (IOException | ClassNotFoundException e){
            SocketAddress clientAddr = null;
            try {
                clientAddr = channel.getRemoteAddress();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            log.info("Разорвано соединение с клиентом "+clientAddr);
            throw new ConnectionCancelledException("Соединение разорвано");
        }
    }
}