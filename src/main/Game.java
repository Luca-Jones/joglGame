package main;

import entities.Block;
import entities.Entity;
import entities.MoveableEntity;
import entities.Player;
import entities.properties.BlockType;
import graphics.Renderer;
import inputs.KeyInputHandler;
import utils.ConcurrentSortedList;

public class Game extends Thread {

    private static final long NANOSECONDS_PER_SECOND = (long) 1e9;
    private static final long UPDATES_PER_SECOND = 100;
    private static final long NANOSECONDS_PER_UPDATE = NANOSECONDS_PER_SECOND / UPDATES_PER_SECOND;
    private static final double TIME_STEP = 0.04;
    private static final int UNTIS_WIDE = 10;

    private ConcurrentSortedList<Entity> entities;
    private boolean isRunning;

    private Renderer renderer;

    public Game(boolean isSinglePlayer) {

        entities = new ConcurrentSortedList<>();
        Player player = new Player("Joe", 1, 1);
        entities.add(player);
        entities.add(new Block(BlockType.SOLID, 0.0f, 0.5f, 1f, 1f));
        entities.add(new Block(BlockType.SOLID, 0.0f, 1.5f, 1f, 1f));
        entities.add(new Block(BlockType.SOLID, 0.0f, 2.5f, 1f, 1f));
        entities.add(new Block(BlockType.PLATFORM,7f, 3f, 2f, 0.25f));
        entities.add(new Block(BlockType.PLATFORM,9f, 3f, 2f, 0.25f));
        entities.add(new Block(BlockType.SOLID, -7.0f, 2.5f, 1f, 1f));
        entities.add(new Block(BlockType.SOLID, -8.0f, 2.5f, 1f, 1f));
        entities.add(new Block(BlockType.SOLID, -9.0f, 2.5f, 1f, 1f));

        if(isSinglePlayer) {
            renderer = new Renderer(UNTIS_WIDE, entities, player);
            renderer.setKeyListener(new KeyInputHandler(player));
        }

        isRunning = true;

    }

    @Override
    public void run() {

        long currentTime = 0;
        long previousTime = System.nanoTime();
        long deltaTime = 0;
        long accumulatedTime = 0;

        while(isRunning) {
            
            currentTime = System.nanoTime();
            deltaTime = currentTime - previousTime;
            previousTime  = currentTime;
            accumulatedTime += deltaTime;

            while (accumulatedTime >= NANOSECONDS_PER_UPDATE) {
                update(TIME_STEP);
                accumulatedTime -= NANOSECONDS_PER_UPDATE;
            }

            render();

        }
    }

    private void update(double deltaTime) {
        
        // renderer and input updates
        if (renderer != null) {
            renderer.update(deltaTime);
        }

        for (Entity entity : entities) {

            entity.update(deltaTime);

            if (entity instanceof MoveableEntity) {
                MoveableEntity moveableEntity = (MoveableEntity) entity;
                
                for (Entity otherEntity : entities) {
                    if (moveableEntity != otherEntity && moveableEntity.isColliding(otherEntity)) {
                        moveableEntity.addCollision(otherEntity);
                    }
                }
    
                moveableEntity.handleCollisions();
            }

        }
        
    }

    private void render() {
        if (renderer != null) {
            renderer.render();
        }
    }

    public void addPlayer(Player player) {}

    public void removePlayer(Player player) {}
    
}
