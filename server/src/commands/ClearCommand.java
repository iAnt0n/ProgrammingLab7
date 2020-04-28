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
        CityDB.clear();
        cm.clear();
        return "Коллекция очищена";
    }
}
