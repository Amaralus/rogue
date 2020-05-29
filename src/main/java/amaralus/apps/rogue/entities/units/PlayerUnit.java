package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.commands.UnitCommand;

import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.PLAYER;
import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;

public class PlayerUnit extends Unit {

    public PlayerUnit() {
        super(PLAYER);
    }

    @Override
    public void update() {
        UnitCommand command = (UnitCommand) gameScreen().getInputCommand();
        command.execute(this);
    }
}
