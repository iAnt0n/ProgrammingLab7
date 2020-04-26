package communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class ResponseHandler extends RecursiveAction{
    private String response;
    private SocketChannel channel;
    private Logger log = Logger.getLogger(ResponseHandler.class.getName());

    public ResponseHandler(SocketChannel channel, String response){
        this.channel=channel;
        this.response=response;
    }


    @Override
    public void compute() {
        ByteBuffer bb = ByteBuffer.wrap((response+"\n").getBytes());
        try {
            channel.write(bb);
            log.info("Ответ отправлен клиенту "+channel.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}