package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду help
 */
public class InfoCommand extends Command {
    public InfoCommand(){
        name = "info";
        helpString = "вывести информацию о коллекции";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        return cm.info();
    }
}
