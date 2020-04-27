package communication;

import java.io.*;

/**
 * Класс, служащий для передачи команд с клиента на сервер
 */
public class TransferObject implements Serializable {
    private final String name;
    private final String[] simpleArgs;
    private final Object complexArgs;
    private final String login;
    private final char[] password;
    private static final long serialVersionUID = 123456789L;

    public TransferObject(String name, String[] simpleArgs, Object complexArgs, String login, char[] password) {
        this.name = name;
        this.simpleArgs = simpleArgs;
        this.complexArgs = complexArgs;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Object getComplexArgs() {
        return complexArgs;
    }

    public String[] getSimpleArgs() {
        return simpleArgs;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }
}

