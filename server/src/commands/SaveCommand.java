package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;

/**
 * Класс, реализующий команду save
 */
public class SaveCommand extends Command {
    public SaveCommand(){
        name = "save";
        helpString = "Сохранить коллекцию в файл";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        try {
            cm.save("out.json");
            return "Коллекция сохранена";
        }
        catch (IOException e){
            return "Ошибка при записи в файл. Коллекция не сохранена";
        }
    }
}
