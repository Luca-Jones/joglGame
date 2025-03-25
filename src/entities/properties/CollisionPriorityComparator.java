package entities.properties;

import java.util.Comparator;

import entities.Entity;

public class CollisionPriorityComparator implements Comparator<Entity> {

    private Entity entity;

    public CollisionPriorityComparator(Entity entity) {
        this.entity = entity;
    }

    // TODO: implement this method
    @Override
    public int compare(Entity o1, Entity o2) {
        return 0 * entity.hashCode();
    }
    
}
