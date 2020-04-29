import commands.CommandBuilder;
import communication.Connector;
import communication.TransferObject;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.*;
import java.net.ConnectException;
import java.time.LocalTime;

public class ClientMain {
    static String login;
    static char[] password;
    static TransferObject verify;
    static Connector connector = null;
    static UserInterface ui = new UserInterface(new InputStreamReader(System.in), new OutputStreamWriter(System.out), true);
    static CommandBuilder cb = new CommandBuilder();
    static OutputStream toServer;
    static BufferedReader fromServer;
    static String[] args1;

    public static void main(String[] args) throws IOException {
        args1=args;
        if (args.length == 2) {
            try {
                connectToServ();
                ui.writeln("Соединение установлено");
            } catch (IllegalArgumentException e) {
                ui.writeln("Неверные параметры запуска");
                System.exit(1);
            }
        } else {
            ui.writeln("Usage: java -jar client17.jar <host> <port>");
            System.exit(1);
        }
        verify();
    }
    public static void connectToServ() {
        LocalTime first =LocalTime.now();
        while (connector==null){
            try{
                connector = Connector.connect(args1[0], Integer.parseInt(args1[1]));
            }catch (IOException e){
                LocalTime second = LocalTime.now();
                if (second.getSecond()-first.getSecond()>10){
                    ui.writeln("Соединение с сервером не может быть восстановлено, простите");
                    System.exit(1);
                }
            }
        }
        toServer = connector.getOut();
        fromServer = new BufferedReader(new InputStreamReader(connector.getIn()));
    }

    public static void verify() throws IOException{
        login = null;
        password = null;
        verify = null;
        while (verify == null) {
            String action = ui.readLineWithMessage("Введите login для входа или register для регистрации: ");
            if (action.equals("login")) {
                login = ui.readLineWithMessage("Введите имя пользователя: ");
                password = ui.readLineWithMessage("Введите пароль: ").toCharArray();
                verify = new TransferObject("login", null, null, login, password);
            } else if (action.equals("register")) {
                login = ui.readLineWithMessage("Введите имя пользователя: ");
                password = ui.readLineWithMessage("Введите пароль: ").toCharArray();
                verify = new TransferObject("register", null, null, login, password);
            } else ui.writeln("Неверная опция");

            if (verify != null) {
                try {
                    sendTO(verify);
                    while (!fromServer.ready()) {
                    }
                    String response = fromServer.readLine();
                    ui.writeln(response);
                    if (!response.equals("Вход произошел успешно")) {
                        verify = null;
                    }
                } catch (IOException e) {
                    ui.writeln("Ошибка при передаче данных");
                }
            }
        }
        ui.writeln("Введите help для просмотра доступных команд");
        execute();
    }

    public static void execute() throws IOException {
        while (ui.hasNextLine() ) {
            try {
                    String cmd = ui.readLine();
                    if (cmd.trim().isEmpty()) {
                        continue;
                    }
                    Object[] cmds = cb.buildCommand(ui, cmd);
                    for (Object o : cmds) {
                        StringBuilder sb = new StringBuilder();
                        TransferObject.Builder transferObjectBuilder = (TransferObject.Builder) o;
                        TransferObject TO = transferObjectBuilder.setLogin(login).setPassword(password).build();
                        sendTO(TO);
                        while (!fromServer.ready()) {
                        }
                        while (fromServer.ready()) {
                            sb.append(fromServer.readLine()).append("\n");
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
    public static void sendTO(TransferObject TO){
        TransferObject TO1 =TO;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(TO);
            oos.flush();
            toServer.write(baos.toByteArray());
        }catch (IOException e){
            ui.writeln("Ошибка при передаче данных. Попробую восстановить соединение");
            connector=null;
            connectToServ();
            ui.writeln("Соединение восстановлено");
            sendTO(TO1);
        }
    }
}
