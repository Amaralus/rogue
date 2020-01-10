package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.field.Cell;
import amaralus.apps.rogue.entities.field.GameField;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.Palette;

import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.graphics.EntitySymbol.*;

public class RoomGenerator {

    public void generate(GameField gameField, int width, int height, int x, int y) {
        List<List<Cell>> roomCells = gameField.getCellLines().subList(y, y + height).stream()
                .map(list -> list.subList(x, x + width))
                .collect(Collectors.toList());

        for (int i = 0; i < roomCells.size(); i++) {
            List<Cell> cellList = roomCells.get(i);

            for (int j = 0; j < cellList.size(); j++) {
                Cell cell = cellList.get(j);
                GraphicsComponent grComponent = cell.getGraphicsComponent();

                if (i == 0 || i == roomCells.size() - 1)
                    grComponent.setEntitySymbol(WALL_HORIZONTAL);
                else if (j == 0 || j == cellList.size() - 1)
                    grComponent.setEntitySymbol(WALL_VERTICAL);
                else {
                    grComponent.setEntitySymbol(CENTRAL_DOT);
                    cell.setCanWalk(true);
                }

                grComponent.setColor(Palette.WHITE_GRAY);
            }
        }

        roomCells.get(0).get(0).getGraphicsComponent().setEntitySymbol(WALL_BOTTOM_RIGHT);
        roomCells.get(0).get(width - 1).getGraphicsComponent().setEntitySymbol(WALL_BOTTOM_LEFT);
        roomCells.get(height - 1).get(0).getGraphicsComponent().setEntitySymbol(WALL_TOP_RIGHT);
        roomCells.get(height - 1).get(width - 1).getGraphicsComponent().setEntitySymbol(WALL_TOP_LEFT);

    }
}
