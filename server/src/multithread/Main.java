package multithread;

import communication.ClientHandler;

import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.logging.Logger;

public class Main {
    static ExecutorService readReq = Executors.newCachedThreadPool();
    static ExecutorService handleReq = Executors.newCachedThreadPool();
    static ForkJoinPool sendRes = new ForkJoinPool();

    public static void main(String[] args) {
        //TODO connect to database
        //TODO add server commands
        //initialize server
        Logger log = Logger.getLogger(ClientHandler.class.getName());
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            int port = Integer.parseInt(args[0]);
            InetSocketAddress address = new InetSocketAddress(port);
            server.socket().bind(address);
            log.info("Сервер запущен: IP " + InetAddress.getLocalHost().getHostAddress() + " Port " + port);
            while (true){
                SocketChannel client = server.accept();
                readReq.execute(new RequestHandler(client));
            }
        }
        catch (BindException e){
            log.warning("Порт уже используется. Укажите другой порт");
            System.exit(1);
        }
        catch (Exception e){
            log.warning("Usage: java -jar server.jar <port> [<filename>]");
            System.exit(1);
        }
    }
}
