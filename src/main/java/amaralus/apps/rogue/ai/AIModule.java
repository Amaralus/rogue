package amaralus.apps.rogue.ai;

import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.units.Unit;

public abstract class AIModule {

    protected final Unit controlledUnit;

    public AIModule(Unit controlledUnit) {
        this.controlledUnit = controlledUnit;
    }

    public abstract UnitCommand getNextUnitAction();
}
