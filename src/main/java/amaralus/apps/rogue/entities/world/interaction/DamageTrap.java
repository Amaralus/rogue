package amaralus.apps.rogue.entities.world.interaction;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.services.game.EventJournal;

import static amaralus.apps.rogue.entities.world.interaction.InteractEntity.Type.TRAP;
import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class DamageTrap extends UpdatedInteractEntity {

    private final Cell cell;

    public DamageTrap(Cell cell) {
        super(TRAP, (Command<Object>) null);
        cellInteractCommand = new Command<>(this::damage);
        this.cell = cell;
    }

    private void damage() {
        if (cell.containsUnit()) {
            int damage = randInt(5, 10);
            cell.getUnit().removeHealthPoints(damage);

            if (cell.getUnit() instanceof PlayerUnit) {
                cell.setGraphicsComponent(GraphicsComponentsPool.TRAP);
                getService(EventJournal.class).logEvent("Ловушка наносит " + damage + " едениц урона!");
            }
        }
    }
}
