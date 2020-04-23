package commands;

import collection.CollectionManager;
import collection.Human;
import communication.TransferObject;

import java.io.IOException;

/**
 * Класс, реализующий команду count_by_governor
 */
public class CountByGovernorCommand extends Command {
    public CountByGovernorCommand(){
        name = "count_by_governor";
        helpString = "{human}: вывести количество элементов, значение поля governor которых равно заданному";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        long l = cm.countByGovernor((Human) TO.getComplexArgs());
        return "В коллекции "+l+" элементов с таким же полем governor";
    }
}
