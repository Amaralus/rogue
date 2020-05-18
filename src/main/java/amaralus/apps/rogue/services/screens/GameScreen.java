package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.ServiceLocator;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.PLAYER;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private MenuScreen gameMenuScreen;
    private InventoryScreen inventoryScreen;

    private Level level;
    private Unit player;

    public GameScreen() {
        screenDrawer = new GameScreenDrawer(this);

        setUpKeyAction();

        initPlayer();
        inventoryScreen = new InventoryScreen(this);

        level = ServiceLocator.levelGenerator().generateLevel();
        level.setUpUnitToRandRoom(player);


    }

    @Override
    public void update() {
        if (inputCommand instanceof UnitCommand)
            ((UnitCommand) inputCommand).execute(player);
        else
            inputCommand.execute();

        inputCommand = Command.NULLABLE_COM;
    }

    private void setUpKeyAction() {
        setUpPlayerKeys();

        commandPool.put(F, new Command<>(((GameScreenDrawer) screenDrawer)::swapWarFogEnabled));
        commandPool.put(I, new Command<>(() -> {
            inventoryScreen.createMenuList();
            setActiveScreen(inventoryScreen);
        }));
        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(gameMenuScreen)));
        commandPool.put(SPACE, new Command<>(() -> {
            level.destroy();
            level = ServiceLocator.levelGenerator().generateLevel();
            level.setUpUnitToRandRoom(player);
        }));
    }

    private void setUpPlayerKeys() {
        commandPool.put(UP, UNIT_MOVE_UP_COM);
        commandPool.put(DOWN, UNIT_MOVE_DOWN_COM);
        commandPool.put(RIGHT, UNIT_MOVE_RIGHT_COM);
        commandPool.put(LEFT, UNIT_MOVE_LEFT_COM);
    }

    private void initPlayer() {
        player = new Unit(PLAYER);
        player.setVisibleRadius(3);
    }

    public void setGameMenuScreen(MenuScreen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }

    public Level getLevel() {
        return level;
    }

    public Unit getPlayer() {
        return player;
    }
}
