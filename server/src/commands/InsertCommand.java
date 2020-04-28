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
    public String execute(CollectionManager cm, TransferObject TO) {
        City city = (City) TO.getComplexArgs();
        city.setCreationDate(LocalDateTime.now());
        try {
            CityDB.insert(city,TO.getSimpleArgs()[0],false);
            cm.put(TO.getSimpleArgs()[0], city);
        }catch(SQLException e){
            return "Возникла ошибка при работе с базой данных, объект не добавлен \n "+e.getMessage();
        }
        return "В коллекцию добавлен город с ключом "+TO.getSimpleArgs()[0];
    }
}
