package communication;

import java.io.*;

/**
 * Класс, служащий для передачи команд с клиента на сервер
 */
public class TransferObject implements Serializable {
    private String name;
    private String[] simpleArgs;
    private Object complexArgs;
    private static final long serialVersionUID = 123456789L;

    public TransferObject(String name, String[] simpleArgs, Object complexArgs) {
        this.name=name;
        this.simpleArgs=simpleArgs;
        this.complexArgs=complexArgs;
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

}

