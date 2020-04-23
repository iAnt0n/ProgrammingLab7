package commands;

import utils.UserInterface;

/**
 * Класс, реализующий команду remove_lower
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand() {
        name = "remove_lower";
        simpleArgLen = 0;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return ui.readCity();
    }
}