package commands;

import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Класс, реализующий команду update
 */
public class UpdateIdCommand extends Command {
    public UpdateIdCommand(){
        name = "update";
        helpString = "id {element} обновить значение элемента коллекции, id которого равен заданному";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        try {
            int id = Integer.parseInt(TO.getSimpleArgs()[0]);
            for (Map.Entry<String, City> e : cm.getCollection().getCityMap().entrySet()) {
                if (e.getValue().getId() == id) {
                    City elem = (City) TO.getComplexArgs();
                    elem.setId(id);
                    elem.setCreationDate(LocalDateTime.now());
                    cm.put(e.getKey(), elem);
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
