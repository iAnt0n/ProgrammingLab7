package commands;

import communication.TransferObject;
import collection.CollectionManager;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;


/**
 * Реализующий singleton класс, который служит для вызова команд
 */
public class CommandInvoker {
    private static CommandInvoker instance;

    public static CommandInvoker getInstance() {
        if (instance == null) {
            instance = new CommandInvoker();
        }
        return instance;
    }

    private HashMap<String, Command> commands = new HashMap<>();

    public CommandInvoker(){
        addCmd(new InfoCommand());
        addCmd(new ShowCommand());
        addCmd(new ClearCommand());
        addCmd(new InsertCommand());
        addCmd(new RemoveKeyCommand());
        addCmd(new UpdateIdCommand());
        addCmd(new SaveCommand());
        addCmd(new CountByGovernorCommand());
        addCmd(new RemoveLowerCommand());
        addCmd(new RemoveLowerKeyCommand());
        addCmd(new ReplaceIfLowerCommand());
        addCmd(new MinByPopulationCommand());
        addCmd(new MaxByStandardOfLivingCommand());
        addCmd(new HelpCommand());
    }

    private void addCmd(Command cmd){
        commands.put(cmd.getName(), cmd);
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }

    public String executeCommand(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        Logger log = Logger.getLogger(CommandInvoker.class.getName());
        Command execCmd = commands.get(TO.getName());
        String response = execCmd.execute(cm, TO);
        log.info("Команда "+TO.getName()+" обработана. Ответ сформирован");
        return response;
    }
}
