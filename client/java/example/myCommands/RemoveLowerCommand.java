package example.myCommands;

import example.MarineValidation.Validator;
import example.managers.Argument;
import example.managers.PreCommandRequest;
import example.objects.SpaceMarine;

public class RemoveLowerCommand extends Command {
    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        SpaceMarine spaceMarine = new Validator().createSpaceMarine(argument.stage(), argument.login(), argument.bundle());
        return new PreCommandRequest(getName(), spaceMarine);
    }
}