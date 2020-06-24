package amaralus.apps.rogue.commands;

import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.InteractEntity.Type;

import java.util.function.Consumer;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.generators.RandomGenerator.randDice6;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.TRAP;
import static amaralus.apps.rogue.services.ServiceLocator.eventJournal;
import static amaralus.apps.rogue.services.ServiceLocator.explorationService;

public class UnitCommand extends Command<Unit> {

    public static final UnitCommand UNIT_MOVE_UP_COM = new UnitCommand(unit -> unit.move(TOP));
    public static final UnitCommand UNIT_MOVE_DOWN_COM = new UnitCommand(unit -> unit.move(BOTTOM));
    public static final UnitCommand UNIT_MOVE_RIGHT_COM = new UnitCommand(unit -> unit.move(RIGHT));
    public static final UnitCommand UNIT_MOVE_LEFT_COM = new UnitCommand(unit -> unit.move(LEFT));

    public static final UnitCommand UNIT_PICK_UP_ITEM_COM = new UnitCommand(unit -> {
        if (unit.getCurrentCell().containsItem()) {
            Item item = unit.getCurrentCell().getItem();
            unit.addItemToInventory(item);
            unit.getCurrentCell().setItem(null);

            if (unit instanceof PlayerUnit)
                eventJournal().logEvent("Подобран предмет " + item.getName() + " " + item.count());
        }
    });
    public static final UnitCommand UNIT_INTERACT_WITH_CELL_COM = new UnitCommand(unit -> unit.getCurrentCell().interact());
    public static final UnitCommand UNIT_SEARCH_AROUND_COM = new UnitCommand(unit -> {
        for (Cell cell : explorationService().aroundUnitAllCells(unit)) {
            if (cell.containsInteractEntity()
                    && cell.getInteractEntity().getType() == Type.TRAP
                    && randDice6())
                cell.setGraphicsComponent(TRAP);
        }
    });
    public static final UnitCommand UNIT_NULLABLE_COM = new UnitCommand(unit -> NULLABLE_COM.execute());

    UnitCommand(Consumer<Unit> unitConsumer) {
        super(unitConsumer);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException();
    }
}
