package commands;

import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;

/**
 * Класс, реализующий команду remove_lower
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand(){
        name = "remove_lower";
        helpString = "{element} удалить из коллекции все элементы, меньшие, чем заданный";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        cm.removeLower((City) TO.getComplexArgs());
        return "Команда выполнена";
    }
}
