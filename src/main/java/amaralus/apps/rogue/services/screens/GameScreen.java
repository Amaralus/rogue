package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.game.EventJournal;
import amaralus.apps.rogue.services.game.GamePlayService;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.services.ServiceLocator.getService;
import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private GamePlayService gamePlayService;
    private TextScreen journalScreen;



    public GameScreen() {
        serviceLocator().register(this);

        gamePlayService = new GamePlayService();
        serviceLocator().register(gamePlayService);
        gamePlayService.initGame();

        screenDrawer = new GameScreenDrawer(this);
        new InventoryScreen();
        journalScreen = new TextScreen(this, "Журнал событий", () -> getService(EventJournal.class).getLastEvents(28));

        setUpKeyAction();
    }

    @Override
    public void update() {
        if (inputCommand instanceof UnitCommand) {
            gamePlayService.getUpdateLoop().update();
        } else
            inputCommand.execute();

        if (getGamePlayService().isGameOver()) {
            setActiveScreen(new GameOverScreen());
        }

        inputCommand = Command.NULLABLE_COM;
    }

    private void setUpKeyAction() {
        setUpPlayerKeys();
        setUpDevelopCheatKeys();

        commandPool.put(I, new Command<>(() -> {
            getService(InventoryScreen.class).setUpMenuList();
            setActiveScreen(getService(InventoryScreen.class));
        }));
        commandPool.put(J, new Command<>(() -> setActiveScreen(journalScreen)));
        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(getService(GameMenuScreen.class))));

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
        commandPool.put(F3, new Command<>((() -> {
            Unit player = getGamePlayService().getPlayer();
            player.setInvulnerable(!player.isInvulnerable());
            getService(EventJournal.class).logEvent("Бессмертие " + (player.isInvulnerable() ? "включено!" : "выключено!"));
        })));
    }

    public GamePlayService getGamePlayService() {
        return gamePlayService;
    }
}
