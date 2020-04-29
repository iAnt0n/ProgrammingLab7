package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду clear
 */
public class ClearCommand extends Command {
    ClearCommand(){
        name = "clear";
        helpString = "очистить коллекцию";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String result = CityDB.clear(TO.getLogin());
        cm.clear();
        if (!result.isEmpty()) return "Команда выполнена, но вам было отказано в доступе к объектам Cty с именами "+result;
        return "Коллекция очищена";
    }
}
