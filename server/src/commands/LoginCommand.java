package commands;

import collection.CollectionManager;
import communication.TransferObject;

public class LoginCommand extends Command {
    public LoginCommand(){
        name = "login";
        helpString = "service";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) {
        return "Неверное имя пользователя или пароль";
    }
}
