package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.world.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.STAIRS;
import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;

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
        Level level= null;

        do {
            if (level != null) level.destroy();

            level = new Level(areaGenerator.generateArea(LEVEL_WIDTH, LEVEL_HEIGHT));
            level.setLevelAreas(areaGenerator.bspSplitArea(level.getGameField()));

            generateRooms(level);
            generateCorridors(level);
        } while (checkLevelForRoomsIslands(level.getRooms()));

        generateStairs(level);

        return level;
    }

    private void generateCorridors(Level level) {
        List<Corridor> corridors = new ArrayList<>();

        for (LevelArea area : level.getLevelAreas()) {
            if (area.containsRoom()) {
                area.getNeighborAreas().stream()
                        .filter(LevelArea::containsRoom)
                        .map(LevelArea::getRoom)
                        .filter(room -> !room.getCorridors().stream()
                                .flatMap(corridor -> corridor.getRooms().stream())
                                .collect(Collectors.toSet()).contains(area.getRoom()))
                        .forEach(room -> corridors.add(corridorGenerator.generateCorridor(area.getRoom(), room)));
            }
        }

        for (int i = 0; i < randInt(5); i++) {
            Corridor corridor = corridorGenerator.generateCorridorToNeighborArea(randElement(level.getRooms()));
            if (corridor != null) corridors.add(corridor);
        }

        level.setCorridors(corridors);
    }

    private boolean checkLevelForRoomsIslands(List<Room> levelRooms) {
        Room startRoom = randElement(levelRooms);

        Set<Room> foundedRooms = new HashSet<>();
        foundedRooms.add(startRoom);

        List<Room> connectedRooms = new ArrayList<>();
        connectedRooms.add(startRoom);

        do {
            for (Room room : connectedRooms) {
                connectedRooms = room.getCorridors().stream()
                        .flatMap(corridor -> corridor.getRooms().stream())
                        .filter(nextRoom -> !foundedRooms.contains(nextRoom))
                        .collect(Collectors.toList());
                foundedRooms.addAll(connectedRooms);
            }
        } while (!connectedRooms.isEmpty());

        return foundedRooms.size() != levelRooms.size();
    }

    private void generateRooms(Level level) {
        int roomCount = randInt(MIN_ROOM_COUNT, level.getLevelAreas().size());
        List<LevelArea> randomAreas = randUniqueElements(level.getLevelAreas(), roomCount);

        List<Room> rooms = new ArrayList<>(roomCount);
        for (LevelArea area : randomAreas) {
            Room room = roomGenerator.generateRoom(area);
            room.setLevelArea(area);
            area.setRoom(room);
            rooms.add(room);
        }

        rooms.forEach(room -> room.setDarkRoom(randBoolean()));

        level.setRooms(rooms);
    }

    private void generateStairs(Level level) {
        Room room = randElement(level.getRooms());
        Cell stairsCell = room.getRandCell();

        room.setExitRoom(true);
        stairsCell.setGraphicsComponent(STAIRS);
        stairsCell.setCanPutItem(false);
        stairsCell.setCellInteractCommand(new Command<>(() -> gameScreen().setRegenerateLevel(true)));
    }
}
