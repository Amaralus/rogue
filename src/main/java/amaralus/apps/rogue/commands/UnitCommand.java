package amaralus.apps.rogue.commands;

import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.CellType;
import amaralus.apps.rogue.entities.world.InteractEntity.Type;
import amaralus.apps.rogue.services.EventJournal;
import amaralus.apps.rogue.services.ExplorationService;

import java.util.function.Consumer;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.generators.RandomGenerator.randDice3;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.DOOR;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.TRAP;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

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
                getService(EventJournal.class).logEvent("Подобран предмет " + item.getName() + " " + item.count());
        }
    });
    public static final UnitCommand UNIT_INTERACT_WITH_CELL_COM = new UnitCommand(unit -> unit.getCurrentCell().interact());
    public static final UnitCommand UNIT_SEARCH_AROUND_COM = new UnitCommand(unit -> {
        for (Cell cell : getService(ExplorationService.class).aroundUnitAllCells(unit)) {
            if (cell.containsInteractEntity()
                    && cell.getInteractEntity().getType() == Type.TRAP
                    && randDice3())
                cell.setGraphicsComponent(TRAP);

            if (cell.getType() == CellType.HIDDEN_DOOR && randDice3()) {
                cell.setCanWalk(true);
                cell.setType(CellType.DOOR);
                cell.setGraphicsComponent(DOOR);
            }
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
