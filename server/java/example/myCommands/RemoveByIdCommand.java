package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.ripManager.SqlConnection;


public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        int ID = (int) commandArgument.argument();
        String name = commandArgument.name();

        if (list.stream().anyMatch(spaceMarine -> spaceMarine.getId() == ID && spaceMarine.getCreator().equals(name))) {
            SqlConnection sqlConnection = new SqlConnection();
            sqlConnection.deleteMarineFromDatabase(ID);
            list.removeIf(spaceMarine -> spaceMarine.getId() == ID);
            return new Product("The marine has been removed", true);
        } else
            return new Product("There is no marine with this ID or you do not have the rights to delete it", false);
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
}