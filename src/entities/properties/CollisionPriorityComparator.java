package entities.properties;

import java.util.Comparator;

import entities.Entity;
import entities.MoveableEntity;

public class CollisionPriorityComparator implements Comparator<Entity> {

    private MoveableEntity moveableEntity;

    public CollisionPriorityComparator(MoveableEntity moveableEntity) {
        this.moveableEntity = moveableEntity;
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        float y1 = o1.getY();
        float y2 = o2.getY();
        float y_ = moveableEntity.getY();
        if (y1 < y_ && y_ < y2) {
            if (y_ - y1 < y2 - y_) {
                return -1;
            } else if (y_ - y1 > y2 - y_) {
                return 1;
            } else {
                return 0;
            }
        } else if (y2 < y_ && y_ < y1) {
            if (y_ - y2 < y1 - y_) {
                return 1;
            } else if (y_ - y2 > y1 - y_) {
                return -1;
            } else {
                return 0;
            }
        }
        float x1 = o1.getX();
        float x2 = o2.getX();
        float x_ = moveableEntity.getX();
        if (x1 < x_ && x_ < x2) {
            if (x_ - x1 < x2 - x_) {
                return -1;
            } else if (x_ - x1 > x2 - x_) {
                return 1;
            } else {
                return 0;
            }
        } else if (x2 < x_ && x_ < x1) {
            if (x_ - x2 < x1 - x_) {
                return 1;
            } else if (x_ - x2 > x1 - x_) {
                return -1;
            } else {
                return 0;
            }
        }
        return 0;
    }
    
}
