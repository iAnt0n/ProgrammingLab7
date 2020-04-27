package communication;

import collection.CollectionManager;
import commands.CommandInvoker;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class CommandHandler implements Callable<String> {
    private TransferObject to;
    private CollectionManager cm;
    private Logger log = Logger.getLogger(RequestHandler.class.getName());

    public CommandHandler(TransferObject to, CollectionManager cm) {
        this.to=to;
        this.cm=cm;
    }

    @Override
    public String call() throws Exception {
        return CommandInvoker.getInstance().executeCommand(cm, to);
    }
}
