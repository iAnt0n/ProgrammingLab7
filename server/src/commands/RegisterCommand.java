package commands;

import DB.ClientDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

public class RegisterCommand extends Command {
    RegisterCommand(){
        name = "register";
        helpString = "service";
    }

    @Override
    public String execute(CollectionManager cm, TransferObject TO) throws SQLException {
        return ClientDB.register(TO.getLogin(), TO.getPassword());
    }
}
