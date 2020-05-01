package commands;

import exceptions.InvalidFieldException;
import utils.UserInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Класс, реализующий команду execute_script
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        name = "execute_script";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        Path path = Paths.get(simpArgs[0]);
        ArrayList<Object> cmds = new ArrayList<>();
        try {
            UserInterface fileInterface = new UserInterface(new BufferedReader(new FileReader(path.toFile())),
                    new OutputStreamWriter(System.out), false);
            ui.writeln("Скрипт обрабатывается");
            while (fileInterface.hasNextLine()) {
                String line = fileInterface.read();
                if(line.trim().split("\\s+")[0].equals("execute_script")){
                    ui.writeln("execute_script игнорируется");
                    continue;
                }
                try {
                    cmds.add(CommandBuilder.getInstance().buildCommand(fileInterface, line)[0]);
                }
                catch (NullPointerException e){
                    ui.writeln("Неизвестные команды игнорируются");
                }
                catch (InvalidFieldException e){
                    ui.writeln("Попытки ввести элемент с недопустимыми значениями полей игнорируются");
                }
            }
            return cmds;
        }
        catch (IOException e) {
            return null;
        }
    }
}
