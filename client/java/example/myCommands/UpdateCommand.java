package example.myCommands;

import example.MarineValidation.Validator;
import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;
import example.managers.UpdateArgument;
import example.objects.SpaceMarine;

public class UpdateCommand extends Command {
    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        try {
            String[] args = argument.arg().split(",");
            int rID = Integer.parseInt(args[0]);
            SpaceMarine marine = new Validator().updateSpaceMarine(argument.stage(), argument.login(), argument.arg(), argument.bundle());
            return new PreCommandRequest("update_by_id", new UpdateArgument(rID, marine));
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("Error executing command: " + e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return "update";
    }
}
