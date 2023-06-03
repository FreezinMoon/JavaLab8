package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;


public class ExitCommand extends Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        System.exit(0);
        return new PreCommandRequest(getName(), null);
    }
}