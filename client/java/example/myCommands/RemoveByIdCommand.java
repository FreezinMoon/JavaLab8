package example.myCommands;

import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;

public class RemoveByIdCommand extends Command {
    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        try {
            return new PreCommandRequest(getName(), Integer.parseInt(argument.arg()));
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("Error executing command: " + e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
}