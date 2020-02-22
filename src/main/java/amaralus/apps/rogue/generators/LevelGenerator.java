package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.entities.world.Room;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.*;

public class LevelGenerator {

    private static final int LEVEL_WIDTH = 120;
    private static final int LEVEL_HEIGHT = 28;
    private static final int MIN_ROOM_COUNT = 6;

    private RoomGenerator roomGenerator;
    private CorridorGenerator corridorGenerator;
    private AreaGenerator areaGenerator;

    public LevelGenerator() {
        roomGenerator = new RoomGenerator();
        corridorGenerator = new CorridorGenerator();
        areaGenerator = new AreaGenerator();
    }

    public Level generateLevel() {
        Level level = new Level(areaGenerator.generateArea(LEVEL_WIDTH, LEVEL_HEIGHT));

        level.setAreas(areaGenerator.bspSplitArea(level.getGameField()));

        generateRooms(level);

        List<Room> rooms = level.getRooms();

        for (int i = 0; i < rooms.size(); i++) {
            int index;
            do {
                index = excRandInt(rooms.size());
            } while (index == i);

            corridorGenerator.generateCorridor(rooms.get(i), rooms.get(index));
        }

        return level;
    }

    private void generateRooms(Level level) {
        int roomCount = randInt(MIN_ROOM_COUNT, level.getAreas().size());
        List<Area> randomAreas = randUniqueElements(level.getAreas(), roomCount);

        List<Room> rooms = new ArrayList<>(roomCount);
        for (Area area : randomAreas) {
            rooms.add(roomGenerator.generateRoom(area));
        }

        level.setRooms(rooms);
    }
}
