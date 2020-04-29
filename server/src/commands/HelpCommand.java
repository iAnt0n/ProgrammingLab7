package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду help
 */
public class HelpCommand extends Command {
    HelpCommand(){
        name = "help";
        helpString = "вывести справку по доступным командам";
        argLen = 0;
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        StringBuilder sb = new StringBuilder();
        for (Command command : CommandInvoker.getInstance().getAllCommands()) {
            if (!command.getHelpString().equals("service")) {
                sb.append(command.getName()).append(" ").append(command.getHelpString()).append("\n");
            }
        }
        sb.append("execute_script file_name считать и исполнить скрипт из указанного файла\nexit сменить пользователя\n");
        return sb.toString();
    }
}
