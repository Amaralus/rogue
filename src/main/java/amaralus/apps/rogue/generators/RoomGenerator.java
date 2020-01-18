package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.FieldArea;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.graphics.DefaultComponentsPool;

import java.util.List;

import static amaralus.apps.rogue.Utils.subField;
import static amaralus.apps.rogue.entities.world.CellType.FLOOR;
import static amaralus.apps.rogue.entities.world.CellType.WALL;
import static amaralus.apps.rogue.generators.RandomGenerator.randInt;
import static amaralus.apps.rogue.graphics.DefaultComponentsPool.*;

public class RoomGenerator {

    public Room generateRoom(FieldArea area) {
        int roomHeight = randInt(5, area.getHeight());
        int roomWidth = randInt(5, area.getWidth());

        if (roomWidth > 20) roomWidth = 20;

        return generateRoom(subField(
                area.getAreaCells(),
                randInt(area.getWidth() - roomWidth),
                randInt(area.getHeight() - roomHeight),
                roomWidth,
                roomHeight));
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
                    cell.setGraphicsComponent(DefaultComponentsPool.ROOM_FLOOR);
                    cell.setType(FLOOR);
                    cell.setCanWalk(true);
                }
            }
        }

        int height = roomCells.size();
        int width = roomCells.get(0).size();

        roomCells.get(0).get(0).setGraphicsComponent(TL_CORNER);
        roomCells.get(0).get(width - 1).setGraphicsComponent(TR_CORNER);
        roomCells.get(height - 1).get(0).setGraphicsComponent(BL_CORNER);
        roomCells.get(height - 1).get(width - 1).setGraphicsComponent(BR_CORNER);

        return new Room(roomCells, roomCells.get(0).get(0).getPosition());
    }
}
