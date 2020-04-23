package multithread;

public class RequestHandler implements Runnable {
    @Override
    public void run() {
        //какая-то обработка запроса
        Main.handleReq.execute(new CommandExec(req));
    }
}
