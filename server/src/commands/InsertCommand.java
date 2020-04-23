package commands;

import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Класс, реализующий команду insert
 */
public class InsertCommand extends Command {
    public InsertCommand(){
        name = "insert";
        helpString = "key {element} добавить новый элемент с заданным ключом";
        argLen = 1;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        City city = (City) TO.getComplexArgs();
        city.setMaxNewId();
        city.setCreationDate(LocalDateTime.now());
        cm.put(TO.getSimpleArgs()[0], city);
        return "В коллекцию добавлен город с ключом "+TO.getSimpleArgs()[0];
    }
}
