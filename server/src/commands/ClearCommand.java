package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду clear
 */
public class ClearCommand extends Command {
    public ClearCommand(){
        name = "clear";
        helpString = "очистить коллекцию";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        cm.clear();
        return "Коллекция очищена";
    }
}
