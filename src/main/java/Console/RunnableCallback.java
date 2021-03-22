package Console;

public class RunnableCallback extends Callback {
    final Runnable callback;

    public RunnableCallback(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void run(Console console) {
        this.callback.run();
    }
}
