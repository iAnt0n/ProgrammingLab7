package commands;

import DB.ClientDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

public class LoginCommand extends Command {
    LoginCommand(){
        name = "login";
        helpString = "service";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        return ClientDB.login(TO.getLogin(), TO.getPassword());
    }
}
