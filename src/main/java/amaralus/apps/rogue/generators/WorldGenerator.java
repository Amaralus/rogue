package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.FieldArea;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.entities.world.GameField;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.generators.RandomGenerator.*;

public class WorldGenerator {

    private GameField gameField;
    private RoomGenerator roomGenerator;
    private CorridorGenerator corridorGenerator;
    private BspAreaGenerator areaGenerator;

    public WorldGenerator(GameField gameField) {
        this.gameField = gameField;
        roomGenerator = new RoomGenerator();
        corridorGenerator = new CorridorGenerator();
        areaGenerator = new BspAreaGenerator();
    }

    public Room generateDungeon() {
        List<FieldArea> areaList = areaGenerator.generateArea(gameField);

        int roomCount = randInt(4, areaList.size());
        areaList = randUniqueElements(areaList, roomCount);

        List<Room> roomList = new ArrayList<>(roomCount);
        for (FieldArea area : areaList) {
            roomList.add(roomGenerator.generateRoom(area));
        }

        for (Room room : roomList) {
            corridorGenerator.generateCorridor(gameField, randPositionFromRoom(room), randPositionFromRoom(randElement(roomList)));
        }

        return randElement(roomList);
    }
}
