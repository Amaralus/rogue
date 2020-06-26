package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.services.screens.GameScreen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

public class ExplorationService {

    public List<Cell> getVisibleCells2(Unit unit) {
        Room room = getService(GameScreen.class).getGamePlayService().getLevel().getRoomByCell(unit.getCurrentCell());

        if (room == null)
            return aroundUnitCells(unit);
        else if (room.isDarkRoom())
            return aroundUnitCells(unit);
        else
            return room.getCells().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
    }

    public List<Cell> aroundUnitAllCells(Unit unit) {
        List<Cell> aroundCells = new ArrayList<>();

        Cell centralCell = unit.getCurrentCell();

        for (Cell cell : Arrays.asList(centralCell, centralCell.getRightCell(), centralCell.getLeftCell())) {
            aroundCells.add(cell);
            aroundCells.add(cell.getTopCell());
            aroundCells.add(cell.getBottomCell());
        }

        return aroundCells.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<Cell> aroundUnitCells(Unit unit) {
        Stream<Cell> cellStream = aroundUnitAllCells(unit).stream();

        cellStream = unit.getCurrentCell().getType() == CORRIDOR ?
                cellStream.filter(cell -> cell.getType() == CORRIDOR || cell.getType() == DOOR)
                : cellStream.filter(cell -> cell.getType() != EMPTY);

        return cellStream.collect(Collectors.toList());
    }
}
