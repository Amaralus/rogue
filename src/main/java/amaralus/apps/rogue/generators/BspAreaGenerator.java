package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.GameField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.generators.RandomGenerator.excRandInt;

public class BspAreaGenerator {

    private static final int MIN_AREA_SIZE = 4;
    private static final int MIN_X_AREA_SIZE = MIN_AREA_SIZE * 4;
    private static final int MIN_Y_AREA_SIZE = MIN_AREA_SIZE * 2;

    public List<Area> generateArea(GameField gameField) {
        List<Area> areas = new ArrayList<>();

        for (Area area : splitByY(gameField.getCellLines(), 2))
            areas.addAll(splitByX(area.getCells(), 2));

        return areas;
    }

    private List<Area> splitByY(List<List<Cell>> fieldList, int deepCount) {
        List<Area> areaList = new ArrayList<>();

        if (deepCount == 0 || fieldList.size() < (MIN_Y_AREA_SIZE * 2))
            areaList.add(new Area(fieldList));
        else {
            int splitVal = RandomGenerator.randInt(MIN_Y_AREA_SIZE, fieldList.size() - MIN_Y_AREA_SIZE);
            areaList.addAll(splitByY(fieldList.subList(0, splitVal), deepCount - 1));
            areaList.addAll(splitByY(fieldList.subList(splitVal, fieldList.size()), deepCount - 1));
        }

        return areaList;
    }

    private List<Area> splitByX(List<List<Cell>> fieldList, int deepCount) {
        List<Area> areaList = new ArrayList<>();

        if (deepCount == 0 || fieldList.get(0).size() < (MIN_X_AREA_SIZE * 2))
            areaList.add(new Area(fieldList));
        else {
            int splitVal = RandomGenerator.randInt(MIN_X_AREA_SIZE, fieldList.get(0).size() - MIN_X_AREA_SIZE);
            areaList.addAll(splitByX(innerSubList(fieldList, 0, splitVal), deepCount - 1));
            areaList.addAll(splitByX(innerSubList(fieldList, splitVal, fieldList.get(0).size()), deepCount - 1));
        }

        return areaList;
    }

    private <E> List<List<E>> innerSubList(List<List<E>> list, int fromIndex, int toIndex) {
        return list.stream()
                .map(innerList -> innerList.subList(fromIndex, toIndex))
                .collect(Collectors.toList());
    }
}
