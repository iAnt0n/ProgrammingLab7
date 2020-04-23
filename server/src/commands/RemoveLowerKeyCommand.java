package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду remove_lower_key
 */
public class RemoveLowerKeyCommand extends Command {
    public RemoveLowerKeyCommand(){
        name = "remove_lower_key";
        helpString = "key удалить из коллекции все элементы, ключ которых меньше, чем заданный";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        cm.removeLowerKey(TO.getSimpleArgs()[0]);
        return "Команда выполнена";
    }
}
