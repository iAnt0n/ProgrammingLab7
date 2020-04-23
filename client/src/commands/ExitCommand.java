package commands;

import utils.UserInterface;

public class ExitCommand extends Command {
    public ExitCommand(){
        name = "exit";
        simpleArgLen = 0;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        System.exit(0);
        return null;
    }
}
