package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Room;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static amaralus.apps.rogue.entities.world.CellType.*;
import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;

public class ExplorationService {

    public List<Cell> getVisibleCells2(Unit unit) {
        Room room = gameScreen().getGamePlayService().getLevel().getRoomByCell(unit.getCurrentCell());

        if (room == null)
            return aroundUnitCells(unit);
        else if (room.isDarkRoom())
            return aroundUnitCells(unit);
        else
            return room.getCells().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
    }

    private List<Cell> aroundUnitCells(Unit unit) {
        List<Cell> aroundCells = new ArrayList<>();

        Cell centralCell = unit.getCurrentCell();

        for (Cell cell : Arrays.asList(centralCell, centralCell.getRightCell(), centralCell.getLeftCell())) {
            aroundCells.add(cell);
            aroundCells.add(cell.getTopCell());
            aroundCells.add(cell.getBottomCell());
        }


        Stream<Cell> cellStream = aroundCells.stream();

        cellStream = centralCell.getType() == CORRIDOR ?
                cellStream.filter(cell -> cell.getType() == CORRIDOR || cell.getType() == DOOR)
                : cellStream.filter(cell -> cell.getType() != EMPTY);

        return cellStream.collect(Collectors.toList());
    }
}
