package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.managers.UpdateArgument;
import example.objects.SpaceMarine;
import example.ripManager.SqlConnection;

public class UpdateByIdCommand extends Command {

    public UpdateByIdCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        int id = ((UpdateArgument) commandArgument.argument()).id();
        SpaceMarine newSpaceMarine = ((UpdateArgument) commandArgument.argument()).spaceMarine();
        String name = commandArgument.name();
        boolean updated = list.stream()
                .filter(sm -> sm.getId() == id && name.equals(sm.getCreator()))
                .findFirst()
                .map(sm -> {
                    if (new SqlConnection().updateMarineInDatabase(newSpaceMarine)) {
                        newSpaceMarine.setId(id);
                        newSpaceMarine.setCreator(sm.getCreator());
                        newSpaceMarine.setCreationDate(sm.getCreationDate());
                        list.set(list.indexOf(sm), newSpaceMarine);
                        return true;
                    } else {
                        return false;
                    }
                })
                .orElse(false);
        if (updated) {
            return new Product("Element with ID " + id + " has been updated.", true);
        } else {
            return new Product("Element with ID " + id + " not found or it doesn't belongs to you", false);
        }
    }
}