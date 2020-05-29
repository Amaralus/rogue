package amaralus.apps.rogue.commands;

import amaralus.apps.rogue.entities.units.Unit;

import java.util.function.Consumer;

import static amaralus.apps.rogue.entities.Direction.*;

public class UnitCommand extends Command<Unit> {

    public static final UnitCommand UNIT_MOVE_UP_COM = new UnitCommand(unit -> unit.move(TOP));
    public static final UnitCommand UNIT_MOVE_DOWN_COM = new UnitCommand(unit -> unit.move(BOTTOM));
    public static final UnitCommand UNIT_MOVE_RIGHT_COM = new UnitCommand(unit -> unit.move(RIGHT));
    public static final UnitCommand UNIT_MOVE_LEFT_COM = new UnitCommand(unit -> unit.move(LEFT));

    public static final UnitCommand UNIT_PICK_UP_ITEM_COM = new UnitCommand(unit -> {
        if (unit.getCurrentCell().containsItem()) {
            unit.addItemToInventory(unit.getCurrentCell().getItem());
            unit.getCurrentCell().setItem(null);
        }
    });
    public static final UnitCommand UNIT_INTERACT_WITH_CELL_COM = new UnitCommand(unit -> unit.getCurrentCell().interact());
    public static final UnitCommand UNIT_NULLABLE_COM = new UnitCommand(unit -> NULLABLE_COM.execute());

    UnitCommand(Consumer<Unit> unitConsumer) {
        super(unitConsumer);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException();
    }
}
