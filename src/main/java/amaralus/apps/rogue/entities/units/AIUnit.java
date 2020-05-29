package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.ai.AIModule;
import amaralus.apps.rogue.graphics.GraphicsComponent;

public class AIUnit extends Unit {

    protected AIModule aiModule;

    public AIUnit(AIModule aiModule, GraphicsComponent graphicsComponent) {
        this(graphicsComponent);
        this.aiModule = aiModule;
    }

    public AIUnit(GraphicsComponent graphicsComponent) {
        super(graphicsComponent);
    }

    @Override
    public void update() {
        aiModule.getNextUnitAction().execute(this);
    }


}
