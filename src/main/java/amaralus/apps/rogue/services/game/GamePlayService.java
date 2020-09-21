package amaralus.apps.rogue.services.game;

import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.items.ItemFactory;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Zombie;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.services.ServiceLocator;

import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.AMULET_OF_YENDOR_PROTOTYPE;
import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.GOLD_PROTOTYPE;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class GamePlayService {

    private boolean regenerateLevel = false;
    private boolean gameOver = false;
    private boolean win = false;

    private Level level;
    private int levelNumber = 1;

    private PlayerUnit player;

    private UpdateLoop updateLoop;

    public GamePlayService() {
        updateLoop = new UpdateLoop(this);
    }

    public void initGame() {
        getService(EventJournal.class).clear();
        getService(EventJournal.class).logEvent("Найди амулет Йендара и выберись назад чтобы победить!");

        player = new PlayerUnit();

        generateLevel();
        levelNumber = 1;
        gameOver = false;
        win = false;

    }

    public void generateLevel() {
        if (level != null) level.destroy();
        updateLoop.clearEntities();

        level = ServiceLocator.getService(LevelGenerator.class).generateLevel(levelNumber);
        updateLevelNumber();

        level.setUpPlayerToRandRoom(player);
        updateLoop.registerEntity(player);

        spawnMonsters();
        setUpAmuletOfYendor();
        initGoldOnTheLevel();
    }

    private void updateLevelNumber() {
        if (player.getInventory().containsItem(2)) {
            if (levelNumber > 1)
                --levelNumber;
        } else
            ++levelNumber;
    }

    private void spawnMonsters() {
        for (int i = 0; i < excRandInt(3, 10 + levelNumber); i++) {
            Zombie zombie = new Zombie();
            if (level.setUpUnitToRandRoom(zombie)) {
                updateLoop.registerEntity(zombie);
                level.getUnits().add(zombie);
            }
        }
    }

    private void setUpAmuletOfYendor() {
        if (levelNumber <= 20 || player.getInventory().containsItem(2) || !randDice3())
            return;

        Item amuletOfYendor = getService(ItemFactory.class).produce(AMULET_OF_YENDOR_PROTOTYPE);
        boolean done;
        do {
            done = level.setUpItemToRandRoom(amuletOfYendor);
        } while (!done);
    }

    private void initGoldOnTheLevel() {
        for (int i = 0; i < randInt(3, 10 + (levelNumber / 2)); i++)
            level.setUpItemToRandRoom(getService(ItemFactory.class).produce(GOLD_PROTOTYPE, randInt(1, 10 + (3 * levelNumber))));
    }

    public boolean isRegenerateLevel() {
        return regenerateLevel;
    }

    public void setRegenerateLevel(boolean regenerateLevel) {
        this.regenerateLevel = regenerateLevel;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Level getLevel() {
        return level;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public PlayerUnit getPlayer() {
        return player;
    }

    public UpdateLoop getUpdateLoop() {
        return updateLoop;
    }
}
