package commands;

import utils.UserInterface;

/**
 * Класс, реализующий команду count_by_governor
 */
public class CountByGovernorCommand extends Command{
    public CountByGovernorCommand(){
        name = "count_by_governor";
        simpleArgLen = 0;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        return ui.readHuman();
    }
}
