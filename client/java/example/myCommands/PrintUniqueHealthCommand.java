package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class PrintUniqueHealthCommand extends Command {

    @Override
    public String getName() {
        return "print_unique_health";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}