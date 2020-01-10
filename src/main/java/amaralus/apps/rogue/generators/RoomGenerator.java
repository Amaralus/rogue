package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.field.Cell;
import amaralus.apps.rogue.entities.field.GameField;

import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.entities.field.CellType.FLOOR;
import static amaralus.apps.rogue.entities.field.CellType.WALL;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.*;

public class RoomGenerator {

    public void generate(GameField gameField, int width, int height, int x, int y) {

        List<List<Cell>> roomCells = gameField.getCellLines().subList(y, y + height).stream()
                .map(list -> list.subList(x, x + width))
                .collect(Collectors.toList());

        for (int i = 0; i < roomCells.size(); i++) {
            List<Cell> cellList = roomCells.get(i);

            for (int j = 0; j < cellList.size(); j++) {
                Cell cell = cellList.get(j);

                cell.setType(WALL);

                if (i == 0 || i == roomCells.size() - 1)
                    cell.setGraphicsComponent(HORIZONTAL_WALL);
                else if (j == 0 || j == cellList.size() - 1)
                    cell.setGraphicsComponent(VERTICAL_WALL);
                else {
                    cell.setGraphicsComponent(FLOOR_ROOM);
                    cell.setType(FLOOR);
                    cell.setCanWalk(true);
                }
            }
        }

        roomCells.get(0).get(0).setGraphicsComponent(TL_CORNER);
        roomCells.get(0).get(width - 1).setGraphicsComponent(TR_CORNER);
        roomCells.get(height - 1).get(0).setGraphicsComponent(BL_CORNER);
        roomCells.get(height - 1).get(width - 1).setGraphicsComponent(BR_CORNER);

    }
}
