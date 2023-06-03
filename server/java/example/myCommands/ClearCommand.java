package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.objects.SpaceMarine;
import example.ripManager.SqlConnection;

public class ClearCommand extends Command {

    public ClearCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        String name = commandArgument.name();
        SqlConnection sqlConnection = new SqlConnection();
        if (list.isEmpty()) {
            return new Product("The list is already empty", false);
        }
        for (SpaceMarine spaceMarine : list) {
            if (spaceMarine.getCreator().equals(name)) {
                sqlConnection.deleteMarineFromDatabase(spaceMarine.getId());
                list.remove(spaceMarine.getId());
            }
        } return new Product("All your marines have been deleted", true);
    }
}