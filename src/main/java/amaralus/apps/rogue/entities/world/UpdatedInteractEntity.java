package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.services.screens.GameScreen;

import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class UpdatedInteractEntity extends InteractEntity implements UpdatedEntity {

    public UpdatedInteractEntity(Type type, Command<Object> cellInteractCommand) {
        super(type, cellInteractCommand);
        register();
    }

    public UpdatedInteractEntity(Type type, Runnable runnable) {
        super(type, runnable);
        register();
    }

    private void register() {
        getService(GameScreen.class).getGamePlayService().getUpdatedEntityList().add(this);
    }

    @Override
    public void update() {
        getCellInteractCommand().execute();
    }
}
