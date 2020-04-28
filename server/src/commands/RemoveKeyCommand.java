package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;


/**
 * Класс, реализующий команду remove_key
 */
public class RemoveKeyCommand extends Command {
    RemoveKeyCommand(){
        name = "remove_key";
        helpString = "key удалить элемент из коллекции по его ключу";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        String key = TO.getSimpleArgs()[0];
        try {
            if (cm.getCollection().getCityMap().containsKey(key)) {
                CityDB.removeKey(key);
                cm.remove(key);
                return "Из коллекции удален город с ключом " + key;
            }else{
                return "Такого ключа в коллекции нет";
            }
        }catch (SQLException e){
            return "Возникли проблемы при работе с Базой Данных \n" + e.getMessage();
        }
    }
}
