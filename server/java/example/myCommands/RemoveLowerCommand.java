package example.myCommands;

import example.managers.CommandArgument;
import example.managers.NameComparator;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.objects.SpaceMarine;
import example.ripManager.SqlConnection;

public class RemoveLowerCommand extends Command {
    NameComparator nameComparator = new NameComparator();

    public RemoveLowerCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        SpaceMarine spaceMarine = (SpaceMarine) commandArgument.argument();
        String name = commandArgument.name();

        list.stream().filter(sm -> nameComparator.compare(sm, spaceMarine) < 0 && name.equals(sm.getCreator())).forEach(sm -> {
            new SqlConnection().deleteMarineFromDatabase(sm.getId());
            list.remove(sm.getId());
        });

        return new Product("All your marines lower than the specified one have been deleted", true);
    }
}