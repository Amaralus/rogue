package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.ServiceLocator;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.GOLD;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.PLAYER;
import static amaralus.apps.rogue.services.ServiceLocator.inventoryScreen;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private Level level;
    private Unit player;

    public GameScreen() {
        ServiceLocator.register(this);
        screenDrawer = new GameScreenDrawer(this);

        setUpKeyAction();

        initPlayer();
        new InventoryScreen();

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
            inventoryScreen().createMenuList();
            setActiveScreen(inventoryScreen());
        }));
        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(ServiceLocator.menuScreen())));
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
        commandPool.put(E, UNIT_PICK_UP_ITEM_COM);
    }

    private void initPlayer() {
        player = new Unit(PLAYER);
        player.setVisibleRadius(3);
        player.addItemToInventory(new Item("Золото", GOLD));
    }

    public Level getLevel() {
        return level;
    }

    public Unit getPlayer() {
        return player;
    }
}
