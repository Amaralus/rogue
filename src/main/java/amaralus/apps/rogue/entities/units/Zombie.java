package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.ai.RandomWalkingAIModule;

import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.ZOMBIE;

public class Zombie extends AIUnit {

    public Zombie() {
        super(ZOMBIE, "Зомби", 50);
        aiModule = new RandomWalkingAIModule(this);
    }
}
