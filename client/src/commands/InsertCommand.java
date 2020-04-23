package commands;

import utils.UserInterface;

/**
 * Класс, реализующий команду insert
 */
public class InsertCommand extends Command {
    public InsertCommand() {
        name = "insert";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return ui.readCity();
    }
}
