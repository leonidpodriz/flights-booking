package Console;

public class ExitCallback extends Callback {

    @Override
    public void run(Console console) {
        System.out.println("Good bye!");
        console.prepareToExit();
    }

    @Override
    public String toString() {
        return "Exit";
    }
}
