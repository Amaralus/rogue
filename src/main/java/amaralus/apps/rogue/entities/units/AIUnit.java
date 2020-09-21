package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.ai.AIModule;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public class AIUnit extends Unit {

    protected AIModule aiModule;

    public AIUnit(AIModule aiModule, GraphicsComponent graphicsComponent, String name, int health) {
        this(graphicsComponent, name, health);
        this.aiModule = aiModule;
    }

    public AIUnit(GraphicsComponent graphicsComponent, String name, int health) {
        super(graphicsComponent, name, health);
    }

    @Override
    public void update() {
        if (isAlive()) aiModule.getNextUnitAction().execute(this);
    }


}
