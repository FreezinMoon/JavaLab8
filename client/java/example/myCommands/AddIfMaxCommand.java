package example.myCommands;

import example.MarineValidation.Validator;
import example.managers.Argument;
import example.managers.PreCommandRequest;
import example.objects.SpaceMarine;

public class AddIfMaxCommand extends Command {

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        SpaceMarine marine = new Validator().createSpaceMarine(argument.stage(), argument.login(), argument.bundle());
        return new PreCommandRequest(getName(), marine);
    }
}