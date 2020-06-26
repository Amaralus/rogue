package amaralus.apps.rogue.entities.units;

import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.services.screens.GameScreen;

import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.PLAYER;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class PlayerUnit extends Unit {

    public PlayerUnit() {
        super(PLAYER, 100);
    }

    @Override
    public void update() {
        UnitCommand command = (UnitCommand) getService(GameScreen.class).getInputCommand();
        command.execute(this);
    }
}
