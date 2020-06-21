package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Zombie;
import amaralus.apps.rogue.entities.world.Level;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.entities.items.ItemPrototypesPool.GOLD_PROTOTYPE;
import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;
import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.services.ServiceLocator.itemFactory;

public class GamePlayService {

    private Level level;
    private int levelNumber = 1;

    private PlayerUnit player;

    private List<UpdatedEntity> updatedEntityList = new ArrayList<>();

    public void initGame() {
        player = new PlayerUnit();
        updatedEntityList.add(player);

        generateLevel();
        levelNumber = 1;

    }

    public void generateLevel() {
        if (level != null) level.destroy();
        updatedEntityList.clear();

        level = ServiceLocator.levelGenerator().generateLevel();
        ++levelNumber;

        level.setUpPlayerToRandRoom(player);
        updatedEntityList.add(0, player);

        spawnMonsters();

        initGoldOnTheLevel();
    }

    private void spawnMonsters() {
        for (int i = 0; i < excRandInt(0, 10); i++) {
            Zombie zombie = new Zombie();
            if (level.setUpUnitToRandRoom(zombie)) {
                updatedEntityList.add(zombie);
                level.getUnits().add(zombie);
            }
        }
    }

    private void initGoldOnTheLevel() {
        for (int i = 0; i < randInt(3, 15); i++)
            level.setUpItemToRandRoom(itemFactory().produce(GOLD_PROTOTYPE, excRandInt(0, 50)));
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
