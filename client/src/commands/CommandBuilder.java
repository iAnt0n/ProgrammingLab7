package commands;

import communication.TransferObject;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс, служащий для построения объекта TransferObject на основе введеной команды
 */
public class CommandBuilder {
    private static CommandBuilder instance;

    public static CommandBuilder getInstance() {
        if (instance == null) {
            instance = new CommandBuilder();
        }
        return instance;
    }
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandBuilder(){
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
        addCmd(new ExecuteScriptCommand());
        addCmd(new HelpCommand());
    }

    private void addCmd(Command cmd){
        commands.put(cmd.getName(), cmd);
    }

    /**
     * Строит объект на основе команды и аргументов, введенных пользователем
     * @param ui интерфейс, служащий для взаимодействия с пользователем
     * @param s строка, на основе которой строится объект
     * @return объект типа {@link TransferObject}
     */
    public Object[] buildCommand(UserInterface ui, String s) throws IOException {
        String[] input = s.trim().split("\\s+");
        String[] simpleArgs = Arrays.copyOfRange(input, 1, input.length);
        Command cmd = commands.get(input[0].toLowerCase());
        if (cmd.getSimpleArgLen() != simpleArgs.length){
            throw new InvalidArgumentsException("Неверные аргументы команды");
        }
        if (cmd.name.equals("execute_script")){
            try {
                return ((ArrayList) cmd.buildArgs(ui, simpleArgs)).toArray();
            }
            catch (NullPointerException e){
                throw new InvalidArgumentsException("Не удается открыть файл скрипта");
            }
        }
        else return new Object[] {new TransferObject.Builder().setName(cmd.getName()).setSimpleArgs(simpleArgs).setComplexArgs(cmd.buildArgs(ui, simpleArgs))};
    }
}
