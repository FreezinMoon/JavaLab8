package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

import java.util.Comparator;

public class PrintFieldDescendingMeleeWeaponCommand extends Command {

    public PrintFieldDescendingMeleeWeaponCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "print_field_descending_melee_weapon";
    }

    @Override
    public Product execute(CommandArgument argument) {
        String result = list.stream()
                .map(sm -> sm.getMeleeWeapon().name())
                .sorted(Comparator.reverseOrder()).toList()
                .toString();
        return new Product(result, true);
    }
}