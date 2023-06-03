package example.objects;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.ZonedDateTime;


public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private static int counter = 1;
    private final float health;
    private final String name;
    private final Coordinates coordinates;
    private final AstartesCategory category;
    private final Weapon weaponType;
    private final MeleeWeapon meleeWeapon;
    private ZonedDateTime creationDate;
    private String creator;
    private int id;
    private Chapter chapter;

    public SpaceMarine(String name, float health, Coordinates coordinates, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, String creator) {
        this.creationDate = ZonedDateTime.now();
        this.id = ++counter;
        if (health > 0 && name.length() > 0 && coordinates != null && category != null && weaponType != null && meleeWeapon != null) {
            this.health = health;
            this.name = name;
            this.coordinates = coordinates;
            this.category = category;
            this.weaponType = weaponType;
            this.meleeWeapon = meleeWeapon;
            this.creator = creator;
        } else throw new NullPointerException("Every argument must be not-null");
    }

    public SpaceMarine(String name, float health, Coordinates coordinates, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter, String creator) {
        this(name, health, coordinates, category, weaponType, meleeWeapon, creator);
        this.chapter = chapter;
    }

    public SpaceMarine(ZonedDateTime creationDate, int id, float health, String name, Coordinates coordinates, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter, String creator) {
        this(creationDate, id, health, name, coordinates, category, weaponType, meleeWeapon, creator);
        counter = Math.max(counter, id);
        this.chapter = chapter;
    }

    public SpaceMarine(ZonedDateTime creationDate, int id, float health, String name, Coordinates coordinates, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, String creator) {
        counter = Math.max(counter, id);
        this.creationDate = creationDate;
        this.id = id;
        this.health = health;
        this.name = name;
        this.coordinates = coordinates;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.creator = creator;

    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        // return such string that it will contain all the information about the object in one line
        return "SpaceMarine{" + "creationDate=" + creationDate + ", id=" + id + ", health=" + health + ", name='" + name + '\'' + ", coordinates=" + coordinates + ", category=" + category + ", weaponType=" + weaponType + ", meleeWeapon=" + meleeWeapon + ", chapter=" + chapter + ", creator='" + creator + '\'' + '}';
    }

    public String getStringToCSV() {
        return id + "," + creationDate.toString() + "," + name + "," + health + "," + coordinates.x() + "," + coordinates.y() + "," + category + "," + weaponType + "," + meleeWeapon + "," + chapter.name() + "," + chapter.marinesCount() + "," + creator;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public float getHealth() {
        return health;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpaceMarine changeId(SpaceMarine sm) {
        sm.setId(++counter);
        return sm;
    }

    public double getX() {
        return coordinates.x();
    }

    public float getY() {
        return coordinates.y();
    }

    public String getChapterName() {
        return chapter.name();
    }

    public Long getMarinesCount() {
        return chapter.marinesCount();
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(@NotNull SpaceMarine sm) {
        return this.name.compareTo(sm.getName());
    }
}