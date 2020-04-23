package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду help
 */
public class HelpCommand extends Command {
    public HelpCommand(){
        name = "help";
        helpString = "вывести справку по доступным командам";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        StringBuilder sb = new StringBuilder();
        for (Command command : CommandInvoker.getInstance().getAllCommands()) {
            sb.append(command.getName()).append(" ").append(command.getHelpString()).append("\n");
        }
        return sb.toString();
    }
}
