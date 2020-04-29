package communication;

import utils.UserInterface;

import java.io.IOException;

public class User {
    private String login;
    private char[] password;

    private User(String login, char[] password){
        this.login=login;
        this.password=password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    public static User getNewUser(UserInterface ui, Connector cnct) throws IOException {
        boolean hasPermission = false;
        String login = null;
        char[] password = null;
        while (!hasPermission) {
            String action = ui.readLineWithMessage("Введите login для входа или register для регистрации или же exit для выхода: ");
            if (action.equals("login")) {
                login = ui.readLineWithMessage("Введите имя пользователя: ");
                password = ui.readLineWithMessage("Введите пароль: ").toCharArray();
                cnct.sendTO(new TransferObject("login", null, null, login, password), ui);
            } else if (action.equals("register")) {
                login = ui.readLineWithMessage("Введите имя пользователя: ");
                password = ui.readLineWithMessage("Введите пароль: ").toCharArray();
                cnct.sendTO(new TransferObject("register", null, null, login, password), ui);
            } else if (action.equals("exit")) {
                System.exit(0);
            } else {
                ui.writeln("Неверная опция");
                continue;
            }
            try {
                while (!cnct.getIn().ready()) {}
                String response = cnct.getIn().readLine();
                ui.writeln(response);
                if (response.equals("Вход произошел успешно")) {
                    hasPermission=true;
                }
            } catch (IOException e) {
                ui.writeln("Ошибка при получении ответа от сервера");
            }
        }
        ui.writeln("Введите help для просмотра доступных команд");
        return new User(login, password);
    }
}
