package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class PrintFieldDescendingCategoryCommand extends Command {

    @Override
    public String getName() {
        return "print_field_descending_category";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}