package example.myCommands;

import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;

public abstract class Command {

    public abstract PreCommandRequest execute(Argument argument) throws CommandExecutionException;

    public abstract String getName();
}
