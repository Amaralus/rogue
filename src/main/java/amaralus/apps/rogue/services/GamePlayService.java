package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Zombie;
import amaralus.apps.rogue.entities.world.Level;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.AMULET_OF_YENDOR_PROTOTYPE;
import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.GOLD_PROTOTYPE;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.services.ServiceLocator.eventJournal;
import static amaralus.apps.rogue.services.ServiceLocator.itemFactory;

public class GamePlayService {

    private boolean gameOver = false;
    private boolean win = false;

    private Level level;
    private int levelNumber = 1;

    private PlayerUnit player;

    private List<UpdatedEntity> updatedEntityList = new ArrayList<>();

    public void initGame() {
        eventJournal().clear();
        eventJournal().logEvent("Найди амулет Йендара и выберись назад чтобы победить!");

        player = new PlayerUnit();
        updatedEntityList.add(player);

        generateLevel();
        levelNumber = 1;
        gameOver = false;
        win = false;

    }

    public void generateLevel() {
        if (level != null) level.destroy();
        updatedEntityList.clear();

        level = ServiceLocator.levelGenerator().generateLevel(levelNumber);
        updateLevelNumber();

        level.setUpPlayerToRandRoom(player);
        updatedEntityList.add(0, player);

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
                updatedEntityList.add(zombie);
                level.getUnits().add(zombie);
            }
        }
    }

    private void setUpAmuletOfYendor() {
        if (levelNumber <= 20 || player.getInventory().containsItem(2) || !randDice3())
            return;

        Item amuletOfYendor = itemFactory().produce(AMULET_OF_YENDOR_PROTOTYPE);
        boolean done;
        do {
            done = level.setUpItemToRandRoom(amuletOfYendor);
        } while (!done);
    }

    private void initGoldOnTheLevel() {
        for (int i = 0; i < randInt(3, 10 + (levelNumber / 2)); i++)
            level.setUpItemToRandRoom(itemFactory().produce(GOLD_PROTOTYPE, randInt(1, 10 + (3 * levelNumber))));
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

    public List<UpdatedEntity> getUpdatedEntityList() {
        return updatedEntityList;
    }
}
