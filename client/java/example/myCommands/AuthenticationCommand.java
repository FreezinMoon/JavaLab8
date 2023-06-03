package example.myCommands;

import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;

public class AuthenticationCommand extends Command {
    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        return new PreCommandRequest(getName(), argument.arg());
    }

    @Override
    public String getName() {
        return "authentication";
    }
}
