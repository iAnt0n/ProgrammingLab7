package commands;

import collection.CollectionManager;
import communication.TransferObject;


/**
 * Класс, реализующий команду remove_key
 */
public class RemoveKeyCommand extends Command {
    public RemoveKeyCommand(){
        name = "remove_key";
        helpString = "key удалить элемент из коллекции по его ключу";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        String key = TO.getSimpleArgs()[0];
        if (cm.getCollection().getCityMap().containsKey(key)){
            cm.remove(key);
            return "Из коллекции удален город с ключом "+key;
        }
        else{
            return "Такого ключа в коллекции нет";
        }
    }
}
