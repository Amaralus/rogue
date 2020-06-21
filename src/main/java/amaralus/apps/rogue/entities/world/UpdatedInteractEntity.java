package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.services.ServiceLocator;

public class UpdatedInteractEntity extends InteractEntity implements UpdatedEntity {

    public UpdatedInteractEntity(Command<Object> cellInteractCommand) {
        super(cellInteractCommand);
        register();
    }

    public UpdatedInteractEntity(Runnable runnable) {
        super(runnable);
        register();
    }

    private void register() {
        ServiceLocator.gameScreen().getGamePlayService().getUpdatedEntityList().add(this);
    }

    @Override
    public void update() {
        getCellInteractCommand().execute();
    }
}
