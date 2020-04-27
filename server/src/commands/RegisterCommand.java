package commands;

import collection.CollectionManager;
import communication.TransferObject;

public class RegisterCommand extends Command {
    public RegisterCommand(){
        name = "register";
        helpString = "service";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        return "Пользователь "+TO.getLogin()+" зарегистрирован";
    }
}
