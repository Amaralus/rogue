package amaralus.apps.rogue.entities.world.interaction;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.services.EventJournal;

import static amaralus.apps.rogue.entities.world.interaction.InteractEntity.Type.TRAP;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class TeleportTrap extends UpdatedInteractEntity {

    private final Level level;
    private final Cell cell;

    public TeleportTrap(Level level, Cell cell) {
        super(TRAP, (Command<Object>) null);
        cellInteractCommand = new Command<>(this::teleport);
        this.level = level;
        this.cell = cell;
    }

    private void teleport() {
        if (cell.containsUnit()) {
            boolean done;
            do {
                done = level.setUpUnitToRandRoom(cell.getUnit());
            } while (!done);

            if (cell.getUnit() instanceof PlayerUnit) {
                cell.setGraphicsComponent(GraphicsComponentsPool.TRAP);
                getService(EventJournal.class).logEvent("Ловушка телепортирует вас в случайную комнату!");
            }
            cell.setUnit(null);
        }
    }
}
