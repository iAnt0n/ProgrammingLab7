package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Класс, реализующий команду insert
 */
public class InsertCommand extends Command {
    InsertCommand(){
        name = "insert";
        helpString = "key {element} добавить новый элемент с заданным ключом";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        City city = (City) TO.getComplexArgs();
        city.setCreationDate(LocalDateTime.now());
        CityDB.insert(city,TO.getSimpleArgs()[0],false);
        city.setId(CityDB.getLastID());
        cm.put(TO.getSimpleArgs()[0], city);
        return "В коллекцию добавлен город с ключом "+TO.getSimpleArgs()[0];
    }
}
