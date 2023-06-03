package example.acht;

import example.managers.Argument;
import example.managers.Invoker;
import example.managers.Product;
import example.objects.*;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Update {
    public static ArrayList<SpaceMarine> getRelevantSpaceMarines(Invoker invoker, String username, String password, Stage stage) {
        Product product = invoker.executeCommand("table", new Argument("", invoker, username, password, stage, null));
        String[] lines = product.getResult().split("\n");
        ArrayList<SpaceMarine> spaceMarines = new ArrayList<>();

        for (String line : lines) {
            String[] values = line.split(",");
            int id = Integer.parseInt(values[0]);
            ZonedDateTime creationDate = ZonedDateTime.parse(values[1]);
            String name = values[2];
            float health = Float.parseFloat(values[3]);
            double x = Double.parseDouble(values[4]);
            Float y = Float.parseFloat(values[5]);
            AstartesCategory category = AstartesCategory.valueOf(values[6]);
            Weapon weaponType = Weapon.valueOf(values[7]);
            MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(values[8]);
            String chapterName = values[9];
            Long marinesCount = Long.parseLong(values[10]);
            String creator = values[11];
            spaceMarines.add(new SpaceMarine(creationDate, id, health, name, new Coordinates(x, y),
                    category, weaponType, meleeWeapon, new Chapter(marinesCount, chapterName), creator));
        }
        return spaceMarines;
    }
}
