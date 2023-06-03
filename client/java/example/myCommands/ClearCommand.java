package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class ClearCommand extends Command {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}