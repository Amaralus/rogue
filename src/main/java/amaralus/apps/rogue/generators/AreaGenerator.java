package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Area;
import amaralus.apps.rogue.entities.world.LevelArea;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaGenerator {

    private static final int MIN_AREA_SIZE = 4;
    private static final int MIN_X_AREA_SIZE = MIN_AREA_SIZE * 4;
    private static final int MIN_Y_AREA_SIZE = MIN_AREA_SIZE * 2;

    public Area generateArea(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException(
                    String.format("width or height must be greater than zero, but was: width=%d height=%d", width, height));

        List<List<Cell>> cells = new ArrayList<>(height);

        for (int y = 0; y < height; y++) {
            List<Cell> cellLine = createCellLine(y, width);
            cells.add(cellLine);

            if (y > 0) connectCellLines(cells.get(y - 1), cellLine);
        }
        return new Area(cells);
    }

    public List<LevelArea> bspSplitArea(Area area) {
        List<List<LevelArea>> areas = new ArrayList<>();

        for (LevelArea subArea : splitByY(area.getCells(), 2))
            areas.add(splitByX(subArea.getCells(), 2));

        connectAreas(areas);

        return areas.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private void connectAreas(List<List<LevelArea>> areas) {
        for (int i = 0; i < areas.size(); i++) {
            List<LevelArea> areaList = areas.get(i);

            for (int j = 0; j < areaList.size(); j++) {
                LevelArea currentArea = areaList.get(j);

                // добавляем зоны одного ряда в соседние
                if (j < areaList.size() - 1) {
                    currentArea.addNeighborArea(areaList.get(j + 1));
                    areaList.get(j + 1).addNeighborArea(currentArea);
                }

                // добавлеем нижние зоны в соседние и наоборот
                if (i < areas.size() - 1) {
                    List<LevelArea> neighborAreas = areas.get(i + 1).stream()
                            .filter(bottomArea -> checkBottomAreaForNeighborhood(currentArea, bottomArea))
                            .collect(Collectors.toList());

                    currentArea.addNeighborAreas(neighborAreas);
                    neighborAreas.forEach(neighborArea -> neighborArea.addNeighborArea(currentArea));
                }
            }
        }
    }

    private boolean checkBottomAreaForNeighborhood(Area currentArea, Area bottomArea) {
        int currentAreaXStart = currentArea.getPosition().x();
        int currentAreaXEnd = currentArea.getBottomRightPosition().x();
        int bottomAreaXStart = bottomArea.getPosition().x();
        int bottomAreaXEnd = bottomArea.getBottomRightPosition().x();

        return (bottomAreaXStart >= currentAreaXStart && bottomAreaXStart <= currentAreaXEnd)
                || (bottomAreaXEnd >= currentAreaXStart && bottomAreaXEnd <= currentAreaXEnd)
                || (bottomAreaXStart < currentAreaXStart && bottomAreaXEnd > currentAreaXEnd);
    }

    private List<Cell> createCellLine(int y, int width) {
        List<Cell> cellLine = new ArrayList<>(width);

        for (int x = 0; x < width; x++) {
            Cell cell = new Cell(Position.of(x, y));
            cellLine.add(cell);

            if (x > 0) {
                Cell prevCell = cellLine.get(x - 1);
                prevCell.setRightCell(cell);
                cell.setLeftCell(prevCell);
            }
        }

        return cellLine;
    }

    private void connectCellLines(List<Cell> topLine, List<Cell> bottomLine) {
        for (int i = 0; i < topLine.size(); i++) {
            Cell topCell = topLine.get(i);
            Cell bottomCell = bottomLine.get(i);

            topCell.setBottomCell(bottomCell);
            bottomCell.setTopCell(topCell);
        }
    }

    private List<LevelArea> splitByY(List<List<Cell>> fieldList, int deepCount) {
        List<LevelArea> areaList = new ArrayList<>();

        if (deepCount == 0 || fieldList.size() < (MIN_Y_AREA_SIZE * 2))
            areaList.add(new LevelArea(fieldList));
        else {
            int splitVal = RandomGenerator.randInt(MIN_Y_AREA_SIZE, fieldList.size() - MIN_Y_AREA_SIZE);
            areaList.addAll(splitByY(fieldList.subList(0, splitVal), deepCount - 1));
            areaList.addAll(splitByY(fieldList.subList(splitVal, fieldList.size()), deepCount - 1));
        }

        return areaList;
    }

    private List<LevelArea> splitByX(List<List<Cell>> fieldList, int deepCount) {
        List<LevelArea> areaList = new ArrayList<>();

        if (deepCount == 0 || fieldList.get(0).size() < (MIN_X_AREA_SIZE * 2))
            areaList.add(new LevelArea(fieldList));
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
