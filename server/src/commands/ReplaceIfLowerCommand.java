package commands;

import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Класс, реализующий команду replace_if_lower
 */
public class ReplaceIfLowerCommand extends Command {
    public ReplaceIfLowerCommand(){
        name = "replace_if_lower";
        helpString = "key {element} заменить значение по ключу, если новое значение меньше старого";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        String key = TO.getSimpleArgs()[0];
        City city = (City) TO.getComplexArgs();
        city.setMaxNewId();
        city.setCreationDate(LocalDateTime.now());
        if (cm.getCollection().getCityMap().containsKey(key)){
            if (cm.replaceIfLower(key, (City) TO.getComplexArgs())){
                return "Замена произошла успешно";
            }
            else return "Новое значение больше старого";
        }
        else return "Такого ключа в коллекции нет";
    }
}
