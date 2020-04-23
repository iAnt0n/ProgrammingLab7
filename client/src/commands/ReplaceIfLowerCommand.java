package commands;

import utils.UserInterface;

/**
 * Класс, реализующий команду replace_if_lower
 */
public class ReplaceIfLowerCommand extends Command {
    public ReplaceIfLowerCommand(){
        name = "replace_if_lower";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return ui.readCity();
    }
}
