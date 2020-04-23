import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import collection.CityCollection;
import collection.CollectionManager;
import commands.CommandInvoker;
import communication.ClientHandler;
import communication.TransferObject;
import exceptions.ConnectionCancelledException;
import utils.JsonReader;

import java.io.*;
import java.net.BindException;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.*;

public class ServerMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logger log = Logger.getLogger(ServerMain.class.getName());
        JsonReader jr = new JsonReader();
        CommandInvoker ci = new CommandInvoker();
        CityCollection collection;
        ClientHandler clientHandler = null;

        try {
            clientHandler = new ClientHandler(Integer.parseInt(args[0]));
        }
        catch (BindException e){
            log.warning("Порт уже используется. Укажите другой порт");
            System.exit(1);
        }
        catch (Exception e){
            log.warning("Usage: java -jar server.jar <port> [<filename>]");
            System.exit(1);
        }

        if (args.length > 1) {
            try {
                collection = new CityCollection(jr.read(args[1]));
                log.info("Инициализирована коллекция из файла "+Paths.get(args[1]).toAbsolutePath().toString());
            } catch (ValueInstantiationException e) {
                collection = new CityCollection();
                log.warning("Инициализирована пустая коллекция. Коллекия в файле "+
                        Paths.get(args[1]).toAbsolutePath().toString()+"не валидна");
            } catch (IOException e) {
                collection = new CityCollection();
                log.warning("Ошибка при попытке чтения из файла "+
                                Paths.get(args[1]).toAbsolutePath().toString()+". Инициализирована пустая коллекция");
            }
        } else {
            collection = new CityCollection();
            log.info("Файл не указан. Инициализирована пустая коллекция");
        }
        CollectionManager cm = new CollectionManager(collection);


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
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    clientHandler.acceptConnection();
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    try {
                        TransferObject TO = clientHandler.readRequest(channel);
                        clientHandler.response = ci.executeCommand(cm, TO) + "\n";
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                    catch (ConnectionCancelledException e){
                        continue;
                    }
                }
                if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    clientHandler.sendResponse(channel);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}
