package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class InfoCommand extends Command {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}