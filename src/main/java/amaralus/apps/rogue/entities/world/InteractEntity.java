package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.commands.Command;

public class InteractEntity {

    private Command<Object> cellInteractCommand;

    public InteractEntity(Command<Object> cellInteractCommand) {
        this.cellInteractCommand = cellInteractCommand;
    }

    public InteractEntity(Runnable runnable) {
        cellInteractCommand = new Command<>(runnable);
    }

    public void interact() {
        cellInteractCommand.execute();
    }

    public Command<Object> getCellInteractCommand() {
        return cellInteractCommand;
    }
}
