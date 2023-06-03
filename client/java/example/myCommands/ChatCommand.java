package example.myCommands;

import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;

public class ChatCommand extends Command {

    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        return new PreCommandRequest(getName(), argument.arg());
    }

    @Override
    public String getName() {
        return "chat";
    }
}
