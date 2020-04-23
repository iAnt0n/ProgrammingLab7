package communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ResponseHandler {
    public static void sendResponse(SocketChannel channel, String response) throws IOException {
        Logger log = Logger.getLogger(ResponseHandler.class.getName());
        ByteBuffer bb = ByteBuffer.wrap(response.getBytes());
        channel.write(bb);
        log.info("Ответ отправлен клиенту "+channel.getRemoteAddress());
    }
}
