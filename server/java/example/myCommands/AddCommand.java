package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.objects.SpaceMarine;
import example.ripManager.SqlConnection;


public class AddCommand extends Command {

    public AddCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        SpaceMarine marine = (SpaceMarine) commandArgument.argument();
        if (new SqlConnection().addMarineToDatabase(marine)) {
            list.add(marine);
            return new Product("New marine has been added to the list", true);
        } else {
            return new Product("Error adding marine to the list", false);
        }
    }

    @Override
    public String getName() {
        return "add";
    }
}