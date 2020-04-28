package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду remove_lower_key
 */
public class RemoveLowerKeyCommand extends Command {
    RemoveLowerKeyCommand(){
        name = "remove_lower_key";
        helpString = "key удалить из коллекции все элементы, ключ которых меньше, чем заданный";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        try{
            CityDB.removeLowerKey(TO.getSimpleArgs()[0]);
            cm.removeLowerKey(TO.getSimpleArgs()[0]);
            return "Команда выполнена";
        } catch (SQLException e ) {
            return "Возникли проблемы при работе с Базой Данных \n" + e.getMessage();
        }
    }
}
