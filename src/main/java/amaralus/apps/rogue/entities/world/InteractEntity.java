package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.commands.Command;

public class InteractEntity {

    private final Type type;

    private Command<Object> cellInteractCommand;

    public InteractEntity(Type type, Command<Object> cellInteractCommand) {
        this.type = type;
        this.cellInteractCommand = cellInteractCommand;
    }

    public InteractEntity(Type type, Runnable runnable) {
        this.type = type;
        cellInteractCommand = new Command<>(runnable);
    }

    public void interact() {
        cellInteractCommand.execute();
    }

    public Command<Object> getCellInteractCommand() {
        return cellInteractCommand;
    }

    public Type getType() {
        return type;
    }

    public enum Type { STAIRS, TRAP }
}
