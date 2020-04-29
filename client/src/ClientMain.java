import commands.CommandBuilder;
import communication.Connector;
import communication.TransferObject;
import communication.User;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        String host = null;
        int port = 0;
        Connector connector = null;
        UserInterface ui = new UserInterface(new InputStreamReader(System.in), new OutputStreamWriter(System.out), true);

        if (args.length == 2) {
            try {
                host = args[0];
                port = Integer.parseInt(args[1]);
                connector = Connector.connectToServ(host, port, ui);
                ui.writeln("Соединение установлено");
            } catch (IllegalArgumentException e) {
                ui.writeln("Неверные параметры запуска");
                ui.writeln("Usage: java -jar client17.jar <host> <port>");
                System.exit(1);
            }
        } else {
            ui.writeln("Usage: java -jar client17.jar <host> <port>");
            System.exit(1);
        }

        User user = User.getNewUser(ui, connector);
        CommandBuilder cb = new CommandBuilder();

        while (ui.hasNextLine() ) {
            try {
                String cmd = ui.readLine();
                if (cmd.trim().isEmpty()) {
                    continue;
                }
                if (cmd.trim().equals("exit")){
                    user = User.getNewUser(ui, connector);
                    continue;
                }
                Object[] cmds = cb.buildCommand(ui, cmd);
                for (Object o : cmds) {
                    StringBuilder sb = new StringBuilder();
                    TransferObject.Builder transferObjectBuilder = (TransferObject.Builder) o;
                    TransferObject TO = transferObjectBuilder.setLogin(user.getLogin()).setPassword(user.getPassword()).build();
                    try {
                        connector.sendTO(TO, ui);
                    }
                    catch (IOException e){
                        ui.writeln("Ошибка при передаче данных. Попробую восстановить соединение");
                        Connector.retainsConnection = false;
                        connector = Connector.connectToServ(host, port, ui);
                        ui.writeln("Соединение восстановлено");
                        connector.sendTO(TO, ui);
                    }
                    while (!connector.getIn().ready()) {}
                    while (connector.getIn().ready()) {
                        sb.append(connector.getIn().readLine()).append("\n");
                    }
                    ui.write(sb.toString());
                }
            }catch (InvalidArgumentsException e) {
                ui.writeln(e.getMessage());
            } catch (NullPointerException e) {
                ui.writeln("Такой команды нет");
            }
        }
    }
}
