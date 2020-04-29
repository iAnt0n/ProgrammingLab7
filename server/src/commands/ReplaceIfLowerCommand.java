package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Класс, реализующий команду replace_if_lower
 */
public class ReplaceIfLowerCommand extends Command {
    ReplaceIfLowerCommand(){
        name = "replace_if_lower";
        helpString = "key {element} заменить значение по ключу, если новое значение меньше старого";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String key = TO.getSimpleArgs()[0];
        City city = (City) TO.getComplexArgs();
        city.setMaxNewId();
        city.setUser(TO.getLogin());
        city.setCreationDate(LocalDateTime.now());
        if (cm.getCollection().getCityMap().containsKey(key)){
            CityDB.replaceIfLower((City) TO.getComplexArgs(), key);
            if (cm.replaceIfLower(key, (City) TO.getComplexArgs(), TO.getLogin())) {
                return "Замена произошла успешно";
            } else return "Новое значение больше старого";
        }
        else return "Такого ключа в коллекции нет";
    }
}
