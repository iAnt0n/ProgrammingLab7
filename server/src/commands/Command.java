package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;

/**
 * Абстрактный класс, описывающий все команды
 */
public abstract class Command {
    protected String name;
    protected String helpString;
    protected int argLen= 0;

    public String getName() {
        return name;
    }

    public String getHelpString() {
        return helpString;
    }

    public int getArgLen() {
        return argLen;
    }

    /**
     * Описывает логику исполнения команды
     * @param cm {@link CollectionManager}, управляющий коллекцией
     * @param TO объект класса {@link TransferObject}, полученный с клиента
     * @return строка - результат выполнения команды
     * @throws IOException если возникают ошибки при работе команд с потоками
     * @throws ClassNotFoundException если объект, являющийся аргументом, не того класса
     */
    public abstract String execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException;
}
