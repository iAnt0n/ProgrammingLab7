package multithread;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ResponseHandler extends RecursiveAction {

    @Override
    protected void compute() {
        //обработка результата выполнения команды
    }
}
