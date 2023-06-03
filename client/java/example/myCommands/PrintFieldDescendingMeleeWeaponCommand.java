package example.myCommands;

import example.managers.Argument;
import example.managers.PreCommandRequest;

public class PrintFieldDescendingMeleeWeaponCommand extends Command {

    @Override
    public String getName() {
        return "print_field_descending_melee_weapon";
    }

    @Override
    public PreCommandRequest execute(Argument argument) {
        return new PreCommandRequest(getName(), null);
    }
}