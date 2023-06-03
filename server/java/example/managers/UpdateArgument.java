package example.managers;

import example.objects.SpaceMarine;

import java.io.Serializable;

public record UpdateArgument(int id, SpaceMarine spaceMarine) implements Serializable {
}