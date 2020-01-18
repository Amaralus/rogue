package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.entities.world.Room;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.*;

public class LevelGenerator {

    private RoomGenerator roomGenerator;
    private CorridorGenerator corridorGenerator;
    private AreaGenerator areaGenerator;

    public LevelGenerator() {
        roomGenerator = new RoomGenerator();
        corridorGenerator = new CorridorGenerator();
        areaGenerator = new AreaGenerator();
    }

    public Level generateLevel() {
        Level level = new Level(areaGenerator.generateArea(120, 30));

        level.setAreas(areaGenerator.bspSplitArea(level.getGameField()));

        generateRooms(level);

        for (Room room : level.getRooms()) {
            corridorGenerator.generateCorridor(
                    level.getGameField(),
                    room.getRandCellPosition(),
                    randElement(level.getRooms()).getRandCellPosition());
        }

        return level;
    }

    private void generateRooms(Level level) {
        int roomCount = randInt(4, level.getAreas().size());
        List<Area> randomAreas = randUniqueElements(level.getAreas(), roomCount);

        List<Room> rooms = new ArrayList<>(roomCount);
        for (Area area : randomAreas) {
            rooms.add(roomGenerator.generateRoom(area));
        }

        level.setRooms(rooms);
    }
}
