package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;

import java.util.List;

import static amaralus.apps.rogue.entities.world.CellType.FLOOR;
import static amaralus.apps.rogue.entities.world.CellType.WALL;
import static amaralus.apps.rogue.entities.world.CellType.WALL_CORNER;
import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.*;

public class RoomGenerator {

    public Room generateRoom(Area area) {
        int roomHeight = randInt(5, area.getHeight());
        int roomWidth = randInt(5, area.getWidth());

        if (roomWidth > 20) roomWidth = 20;

        return generateRoom(area.subArea(
                randInt(area.getWidth() - roomWidth),
                randInt(area.getHeight() - roomHeight),
                roomWidth,
                roomHeight)
                .getCells());
    }

    public Room generateRoom(List<List<Cell>> roomCells) {

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
                    cell.setGraphicsComponent(GraphicsComponentsPool.ROOM_FLOOR);
                    cell.setType(FLOOR);
                    cell.setCanWalk(true);
                    cell.setCanPutItem(true);
                }
            }
        }

        int height = roomCells.size();
        int width = roomCells.get(0).size();

        roomCells.get(0).get(0).setGraphicsComponent(TL_CORNER);
        roomCells.get(0).get(0).setType(WALL_CORNER);
        roomCells.get(0).get(width - 1).setGraphicsComponent(TR_CORNER);
        roomCells.get(0).get(width - 1).setType(WALL_CORNER);
        roomCells.get(height - 1).get(0).setGraphicsComponent(BL_CORNER);
        roomCells.get(height - 1).get(0).setType(WALL_CORNER);
        roomCells.get(height - 1).get(width - 1).setGraphicsComponent(BR_CORNER);
        roomCells.get(height - 1).get(width - 1).setType(WALL_CORNER);

        return new Room(roomCells);
    }
}
