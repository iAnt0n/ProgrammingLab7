package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду remove_lower
 */
public class RemoveLowerCommand extends Command {
    RemoveLowerCommand(){
        name = "remove_lower";
        helpString = "{element} удалить из коллекции все элементы, меньшие, чем заданный";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String result = CityDB.removeLower((City) TO.getComplexArgs(),TO.getLogin());
        cm.removeLower((City) TO.getComplexArgs());
        if (!result.isEmpty()) return "Команда выполнена, но вам было отказано в доступе к объектам Cty с именами "+result;
        return "Команда выполнена";
    }
}
