package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class ShowCommand extends Command {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}