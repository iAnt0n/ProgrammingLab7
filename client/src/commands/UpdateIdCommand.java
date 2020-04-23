package commands;

import utils.UserInterface;

/**
 * Класс, реализующий команду update
 */
public class UpdateIdCommand extends Command {
    public UpdateIdCommand(){
        name = "update";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return ui.readCity();
    }
}
