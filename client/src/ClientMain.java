import commands.CommandBuilder;
import communication.Connector;
import communication.TransferObject;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.*;

public class ClientMain {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface(new InputStreamReader(System.in),
                                             new OutputStreamWriter(System.out), true);
        CommandBuilder cb = new CommandBuilder();
        Connector connector = null;
        if (args.length==2) {
            try {
                connector = Connector.connect(args[0], Integer.parseInt(args[1]));
                ui.writeln("Соединение установлено. Введите help для просмотра доступных команд");
            } catch (IOException e) {
                ui.writeln("Не удалось подключиться к серверу. " + e.getMessage());
                System.exit(1);
            } catch (IllegalArgumentException e) {
                ui.writeln("Неверные параметры запуска");
                System.exit(1);
            }
        }
        else{
            ui.writeln("Usage: java -jar client17.jar <host> <port>");
            System.exit(1);
        }

        OutputStream toServer = connector.getOut();
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(connector.getIn()));

        /*
        Console console = System.console();
        String login = ui.readLine();
        char[] password = console.readPassword();
        checkPassword();
        Arrays.fill(password, ' ');
        */
        while (ui.hasNextLine()) {
            try {
                String cmd = ui.readLine();
                if (cmd.trim().isEmpty()){
                    continue;
                }
                Object[] cmds = cb.buildCommand(ui, cmd);
                    for (Object o: cmds) {
                        StringBuilder sb = new StringBuilder();
                        TransferObject TO = (TransferObject) o;
                        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream (baos)) {
                            oos.writeObject(TO);
                            oos.flush();
                            toServer.write(baos.toByteArray());
                        }
                        while (!fromServer.ready()) {
                        }
                        while (fromServer.ready()) {
                            sb.append(fromServer.readLine()).append("\n");
                        }
                        ui.write(sb.toString());
                    }
            }
            catch(InvalidArgumentsException e){
                ui.writeln(e.getMessage());
            }
            catch (NullPointerException e){
                ui.writeln("Такой команды нет");
            }
            catch (IOException e){
                ui.writeln("Ошибка при передаче данных на сервер");
            }
        }
    }
}
