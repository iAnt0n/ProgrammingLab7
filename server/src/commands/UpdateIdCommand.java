package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Класс, реализующий команду update
 */
public class UpdateIdCommand extends Command {
    UpdateIdCommand(){
        name = "update";
        helpString = "id {element} обновить значение элемента коллекции, id которого равен заданному";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO)  {
        try {
            int id = Integer.parseInt(TO.getSimpleArgs()[0]);
            for (Map.Entry<String, City> e : cm.getCollection().getCityMap().entrySet()) {
                if (e.getValue().getId() == id) {
                    City elem = (City) TO.getComplexArgs();
                    elem.setId(id);
                    elem.setCreationDate(LocalDateTime.now());
                    try{
                        CityDB.updateID(elem,id);
                        cm.put(e.getKey(), elem);
                    }catch (SQLException s){
                        return "Возникли проблемы при работе с Базой Данных \n"+s.getMessage();
                    }

                    return "Элемент с ID " + id + " обновлен";
                }
            }
            return "Нет элемента с таким ID";
        }
        catch (NumberFormatException e){
            return "Неверный формат ID";
        }
    }
}
