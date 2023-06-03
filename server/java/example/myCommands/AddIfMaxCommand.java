package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.NameComparator;
import example.managers.Product;
import example.objects.SpaceMarine;
import example.ripManager.SqlConnection;

public class AddIfMaxCommand extends Command {
    NameComparator nameComparator = new NameComparator();

    public AddIfMaxCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        SpaceMarine spaceMarine = (SpaceMarine) commandArgument.argument();
        boolean added = list.stream()
                .max(nameComparator)
                .map(maxSpaceMarine -> nameComparator.compare(spaceMarine, maxSpaceMarine) > 0)
                .orElse(true);
        if (added) {
            if (new SqlConnection().addMarineToDatabase(spaceMarine)) {
                list.add(spaceMarine);
                return new Product("New marine has been added to the list", true);
            } else {
                return new Product("Error adding marine to the list", false);
            }
        } else return new Product("The space marine has not been added to the list.", false);
    }
}