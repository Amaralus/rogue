package amaralus.apps.rogue.services.game;

import amaralus.apps.rogue.entities.UpdatedEntity;
import amaralus.apps.rogue.entities.units.PlayerUnit;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateLoop {

    private GamePlayService gamePlayService;

    private List<UpdatedEntity> updatedEntityList = new ArrayList<>(100);

    public UpdateLoop(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    public void update() {
        for (int i = 0; updateLoopCondition(i); i++) {
            updatedEntityList.get(i).update();
        }
        clearDeadUnits();
        checkPostUpdateConditions();
    }

    private boolean updateLoopCondition(int i) {
        return i < updatedEntityList.size()
                && !gamePlayService.isRegenerateLevel()
                && !gamePlayService.isGameOver();
    }

    private void checkPostUpdateConditions() {
        if (!gamePlayService.getPlayer().isAlive()) {
            gamePlayService.setWin(false);
            gamePlayService.setGameOver(true);
        }

        if (gamePlayService.isRegenerateLevel()) {
            gamePlayService.setRegenerateLevel(false);
            gamePlayService.generateLevel();
        }
    }

    private void clearDeadUnits() {
        List<Unit> deadUnits = updatedEntityList.stream()
                .filter(entity -> entity instanceof Unit)
                .map(Unit.class::cast)
                .filter(unit -> !unit.isAlive())
                .collect(Collectors.toList());

        updatedEntityList.removeAll(deadUnits);
        for (Unit deadUnit : deadUnits) {
            Cell cell = deadUnit.getCurrentCell();
            cell.setUnit(null);
            deadUnit.destroy();
        }
    }

    public void registerEntity(UpdatedEntity entity) {
        if (entity instanceof PlayerUnit)
            updatedEntityList.add(0, entity);
        else
            updatedEntityList.add(entity);
    }

    public void removeEntity(UpdatedEntity entity) {
        updatedEntityList.remove(entity);
    }

    public void clearEntities() {
        updatedEntityList.clear();
    }
}
