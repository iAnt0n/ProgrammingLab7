package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду show
 */
public class ShowCommand extends Command {
    public ShowCommand(){
        name = "show";
        helpString = "вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        return cm.show();
    }
}
