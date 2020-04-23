package multithread;

import communication.ResponseHandler;

public class CommandExec implements Runnable {
    @Override
    public void run() {
        //обработка выполнения команды
        Main.sendRes.execute(new ResponseHandler(res));
    }
}
