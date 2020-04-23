package commands;

import utils.UserInterface;

/**
 * Абстрактный класс, описывающий все команды
 */
public abstract class Command {
    protected String name;
    protected int simpleArgLen = 0;

    public String getName() {
        return name;
    }

    public int getSimpleArgLen() {
        return simpleArgLen;
    }

    /**
     * Служит для построения аргументов команды, не являющихся примитивным типом (e.g City)
     * @param ui интерфейс для взаимодействия с пользователем
     * @param simpArgs аргументы команды
     * @return аргумент, являющийся объектом класса, который требуется команде для выполнения
     */
    public Object buildArgs(UserInterface ui, String[] simpArgs) {return null;}
}
