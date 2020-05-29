package amaralus.apps.rogue.ai;

import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.units.Unit;

import java.util.Arrays;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.generators.RandomGenerator.randBoolean;
import static amaralus.apps.rogue.generators.RandomGenerator.randElement;

public class RandomWalkingAIModule extends AIModule {

    public RandomWalkingAIModule(Unit controlledUnit) {
        super(controlledUnit);
    }

    @Override
    public UnitCommand getNextUnitAction() {
        if (randBoolean())
            return UnitCommand.UNIT_NULLABLE_COM;
        else
            return randElement(Arrays.asList(UNIT_MOVE_UP_COM, UNIT_MOVE_DOWN_COM, UNIT_MOVE_LEFT_COM, UNIT_MOVE_RIGHT_COM));
    }
}
