package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
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
    public String execute(CollectionManager cm, TransferObject TO)  {
        try {
            CityDB.removeLower((City) TO.getComplexArgs());
            cm.removeLower((City) TO.getComplexArgs());
            return "Команда выполнена";
        }catch (SQLException e ){
            return "Возникли проблемы при работе с Базой Данных \n" +e.getMessage();
        }
    }
}
