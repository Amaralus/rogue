package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.world.*;
import amaralus.apps.rogue.entities.world.interaction.DamageTrap;
import amaralus.apps.rogue.entities.world.interaction.InteractEntity;
import amaralus.apps.rogue.entities.world.interaction.InteractEntity.Type;
import amaralus.apps.rogue.entities.world.interaction.TeleportTrap;
import amaralus.apps.rogue.services.game.GamePlayService;
import amaralus.apps.rogue.services.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static amaralus.apps.rogue.entities.Direction.BOTTOM;
import static amaralus.apps.rogue.entities.Direction.TOP;
import static amaralus.apps.rogue.generators.RandomGenerator.*;
import static amaralus.apps.rogue.graphics.GraphicsComponentsPool.*;
import static amaralus.apps.rogue.services.ServiceLocator.getService;

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

    public Level generateLevel(int levelNumber) {
        Level level = null;

        do {
            if (level != null) level.destroy();

            level = new Level(areaGenerator.generateArea(LEVEL_WIDTH, LEVEL_HEIGHT));
            level.setLevelAreas(areaGenerator.bspSplitArea(level.getGameField()));

            generateRooms(level, levelNumber);
            generateCorridors(level);
        } while (checkLevelForRoomsIslands(level.getRooms()));

        setUpHiddenDoors(level);
        generateStairs(level);
        generateTraps(level, levelNumber);

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

    private void setUpHiddenDoors(Level level) {
        List<Cell> doors = level.getRooms().stream()
                .map(Area::getCells)
                .flatMap(List::stream)
                .flatMap(List::stream)
                .filter(cell -> cell.getType() == CellType.DOOR)
                .collect(Collectors.toList());

        for (Cell cell : randUniqueElements(doors, randInt(1, doors.size() / 3))) {
            cell.setCanWalk(false);
            cell.setType(CellType.HIDDEN_DOOR);

            for (Direction direction : Direction.values()) {
                if (direction.nextCell(cell).getType() == CellType.FLOOR) {
                    if (direction == TOP || direction == BOTTOM)
                        cell.setGraphicsComponent(HORIZONTAL_WALL);
                    else
                        cell.setGraphicsComponent(VERTICAL_WALL);
                    break;
                }
            }
        }
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

    private void generateRooms(Level level, int levelNumber) {
        int roomCount = randInt(MIN_ROOM_COUNT, level.getLevelAreas().size());
        List<LevelArea> randomAreas = randUniqueElements(level.getLevelAreas(), roomCount);

        List<Room> rooms = new ArrayList<>(roomCount);
        for (LevelArea area : randomAreas) {
            Room room = roomGenerator.generateRoom(area);
            room.setLevelArea(area);
            area.setRoom(room);
            rooms.add(room);
        }

        randUniqueElementsPercent(rooms, 8 * levelNumber).forEach(room -> room.setDarkRoom(true));

        level.setRooms(rooms);
    }

    private void generateStairs(Level level) {
        Room room = randElement(level.getRooms());
        Cell stairsCell = room.getRandCell();

        room.setExitRoom(true);
        stairsCell.setGraphicsComponent(STAIRS);
        stairsCell.setCanPutItem(false);
        stairsCell.setInteractEntity(new InteractEntity(Type.STAIRS, () -> {
            int levelNumber = getService(GameScreen.class).getGamePlayService().getLevelNumber();
            boolean playerContainsAmulet = getService(GameScreen.class).getGamePlayService().getPlayer().getInventory().containsItem(2);

            if (playerContainsAmulet && levelNumber == 1) {
                getService(GameScreen.class).getGamePlayService().setGameOver(true);
                getService(GameScreen.class).getGamePlayService().setWin(true);
            } else
                getService(GamePlayService.class).setRegenerateLevel(true);
        }));
    }

    private void generateTraps(Level level, int levelNumber) {
        for (int i = 0; i < randInt(2, 5 + (levelNumber / 2)); i++) {
            Cell cell = getCellForTrap(randElement(level.getRooms()));

            cell.setCanPutItem(false);
            cell.setInteractEntity(randBoolean() ? new TeleportTrap(level, cell) : new DamageTrap(cell));
        }
    }

    private Cell getCellForTrap(Room room) {
        Cell cell;
        boolean goodCell = false;
        do {
            cell = room.getRandCell();

            Cell celForLambda = cell;
            boolean noDoorsAround = !Stream.of(Direction.values())
                    .map(direction -> direction.nextCell(celForLambda).getType())
                    .collect(Collectors.toSet())
                    .contains(CellType.DOOR);

            if (!cell.containsInteractEntity() && noDoorsAround)
                goodCell = true;
        } while (!goodCell);
        return cell;
    }
}
