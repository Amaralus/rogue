package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.GamePlayService;
import amaralus.apps.rogue.services.ServiceLocator;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.services.ServiceLocator.eventJournal;
import static amaralus.apps.rogue.services.ServiceLocator.inventoryScreen;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private GamePlayService gamePlayService;
    private TextScreen journalScreen;

    private boolean regenerateLevel = false;

    public GameScreen() {
        ServiceLocator.register(this);

        gamePlayService = new GamePlayService();
        gamePlayService.initGame();

        screenDrawer = new GameScreenDrawer(this);
        new InventoryScreen();
        journalScreen = new TextScreen(this, "Журнал событий", () -> eventJournal().getLastEvents(28));

        setUpKeyAction();
    }

    @Override
    public void update() {
        if (inputCommand instanceof UnitCommand) {
            for (int i = 0; updateCycleCondition(i) ; i++) {
                UpdatedEntity updatedEntity = gamePlayService.getUpdatedEntityList().get(i);
                updatedEntity.update();
            }
        } else
            inputCommand.execute();

        if (gamePlayService.getPlayer().getHealth() < 1) {
            gamePlayService.setWin(false);
            gamePlayService.setGameOver(true);
        }

        if (getGamePlayService().isGameOver()) {
            setActiveScreen(new GameOverScreen());
        }

        if (regenerateLevel) {
            regenerateLevel = false;
            gamePlayService.generateLevel();
        }

        inputCommand = Command.NULLABLE_COM;
    }

    private boolean updateCycleCondition(int i) {
        return i < gamePlayService.getUpdatedEntityList().size() && !regenerateLevel && !getGamePlayService().isGameOver();
    }

    private void setUpKeyAction() {
        setUpPlayerKeys();
        setUpDevelopCheatKeys();

        commandPool.put(I, new Command<>(() -> {
            inventoryScreen().setUpMenuList();
            setActiveScreen(inventoryScreen());
        }));
        commandPool.put(J, new Command<>(() -> setActiveScreen(journalScreen)));
        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(ServiceLocator.gameMenuScreen())));

    }

    private void setUpPlayerKeys() {
        commandPool.put(UP, UNIT_MOVE_UP_COM);
        commandPool.put(DOWN, UNIT_MOVE_DOWN_COM);
        commandPool.put(RIGHT, UNIT_MOVE_RIGHT_COM);
        commandPool.put(LEFT, UNIT_MOVE_LEFT_COM);
        commandPool.put(E, UNIT_INTERACT_WITH_CELL_COM);
        commandPool.put(T, UNIT_PICK_UP_ITEM_COM);
        commandPool.put(S, UNIT_SEARCH_AROUND_COM);
    }

    private void setUpDevelopCheatKeys() {
        commandPool.put(F1, new Command<>(((GameScreenDrawer) screenDrawer)::swapWarFogEnabled));
        commandPool.put(F2, new Command<>(gamePlayService::generateLevel));
    }

    public void setRegenerateLevel(boolean regenerateLevel) {
        this.regenerateLevel = regenerateLevel;
    }

    public GamePlayService getGamePlayService() {
        return gamePlayService;
    }
}
