package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.commands.UnitCommand;
import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.commands.UnitCommand.*;
import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.GOLD_PROTOTYPE;
import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;
import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.services.ServiceLocator.inventoryScreen;
import static amaralus.apps.rogue.services.ServiceLocator.itemFactory;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private Level level;
    private Unit player;

    List<UpdatedEntity> updatedEntityList = new ArrayList<>();

    public GameScreen() {
        ServiceLocator.register(this);
        screenDrawer = new GameScreenDrawer(this);

        setUpKeyAction();

        initPlayer();
        new InventoryScreen();

        generateLevel();
    }

    @Override
    public void update() {
        if (inputCommand instanceof UnitCommand)
            for (UpdatedEntity updatedEntity : updatedEntityList)
                updatedEntity.update();
        else
            inputCommand.execute();

        inputCommand = Command.NULLABLE_COM;
    }

    private void setUpKeyAction() {
        setUpPlayerKeys();

        commandPool.put(F, new Command<>(((GameScreenDrawer) screenDrawer)::swapWarFogEnabled));
        commandPool.put(I, new Command<>(() -> {
            inventoryScreen().setUpMenuList();
            setActiveScreen(inventoryScreen());
        }));
        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(ServiceLocator.gameMenuScreen())));
        commandPool.put(SPACE, new Command<>(this::generateLevel));
    }

    private void setUpPlayerKeys() {
        commandPool.put(UP, UNIT_MOVE_UP_COM);
        commandPool.put(DOWN, UNIT_MOVE_DOWN_COM);
        commandPool.put(RIGHT, UNIT_MOVE_RIGHT_COM);
        commandPool.put(LEFT, UNIT_MOVE_LEFT_COM);
        commandPool.put(E, UNIT_PICK_UP_ITEM_COM);
    }

    private void initPlayer() {
        player = new PlayerUnit();
        player.setVisibleRadius(3);
        updatedEntityList.add(player);
    }

    private void generateLevel() {
        if (level != null) level.destroy();
        level = ServiceLocator.levelGenerator().generateLevel();
        level.setUpUnitToRandRoom(player);
        initGoldOnTheLevel();
    }

    private void initGoldOnTheLevel() {
        for (int i = 0; i < randInt(3, 15); i++)
            level.setUpItemToRandRoom(itemFactory().produce(GOLD_PROTOTYPE, excRandInt(0, 50)));
    }

    public Level getLevel() {
        return level;
    }

    public Unit getPlayer() {
        return player;
    }
}
