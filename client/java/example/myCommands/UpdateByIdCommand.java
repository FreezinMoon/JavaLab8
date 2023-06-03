package example.myCommands;

import example.MarineValidation.Validator;
import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;
import example.managers.UpdateArgument;
import example.objects.SpaceMarine;

public class UpdateByIdCommand extends Command {
    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        try {
            int rID = Integer.parseInt(argument.arg());
            SpaceMarine marine = new Validator().createSpaceMarine(argument.stage(), argument.login(), argument.bundle());
            return new PreCommandRequest(getName(), new UpdateArgument(rID, marine));
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("Error executing command: " + e.getMessage(), e);
        }
    }
}
