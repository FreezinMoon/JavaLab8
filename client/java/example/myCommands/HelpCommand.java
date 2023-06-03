package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class HelpCommand extends Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}