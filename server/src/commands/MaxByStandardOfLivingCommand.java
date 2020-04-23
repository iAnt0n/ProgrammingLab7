package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.util.NoSuchElementException;

/**
 * Класс, реализующий команду max_by_standard_of_living
 */
public class MaxByStandardOfLivingCommand extends Command {
    public MaxByStandardOfLivingCommand(){
        name = "max_by_standard_of_living";
        helpString = "вывести любой объект из коллекции, значение поля standardOfLiving которого является максимальным";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        try{
            return cm.max_by_standard_of_living();
        }
        catch(NoSuchElementException e){
            return "Поля всех элементов пусты";
        }
    }
}
