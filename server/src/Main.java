import collection.CityCollection;
import collection.CollectionManager;
import commands.CommandInvoker;
import communication.*;
import exceptions.ConnectionCancelledException;

import java.io.IOException;
import java.net.BindException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Main {
    private static ExecutorService readRequest = Executors.newCachedThreadPool();
    private static ExecutorService getResponse = Executors.newCachedThreadPool();
    private static ForkJoinPool sendResponse = new ForkJoinPool();

    public static void main(String[] args) throws IOException {
        Logger log = Logger.getLogger(Main.class.getName());
        CommandInvoker ci = new CommandInvoker();
        CityCollection collection;
        ClientHandler clientHandler = null;

        try {
            clientHandler = new ClientHandler(Integer.parseInt(args[0]));
        } catch (BindException e) {
            log.warning("Порт уже используется. Укажите другой порт");
            System.exit(1);
        } catch (Exception e) {
            log.warning("Usage: java -jar server.jar <port> [<filename>]");
            System.exit(1);
        }

        collection = new CityCollection();
        CollectionManager cm = new CollectionManager(collection);



        HashMap<SelectionKey, Future<TransferObject>> to = new HashMap<>();
        HashMap<SelectionKey, Future<String>> res = new HashMap<>();
        while (true) {
            clientHandler.getSelector().select();
            Iterator keys = clientHandler.getSelector().selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    clientHandler.acceptConnection();
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    try {
                        to.put(key, readRequest.submit(new RequestHandler(channel)));
                        key.interestOps(SelectionKey.OP_WRITE);
                    } catch (ConnectionCancelledException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            if (to.containsKey(key)&&to.get(key).isDone()){
                                res.put(key, getResponse.submit(new CommandHandler(to.get(key).get(), cm)));
                                System.out.println("added response in future");
                                to.remove(key);
                            }
                            if (res.containsKey(key)&&res.get(key).isDone()) {
                                sendResponse.execute(new ResponseHandler(channel, res.get(key).get()));
                                res.remove(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}