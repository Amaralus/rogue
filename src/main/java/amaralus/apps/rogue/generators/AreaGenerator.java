package amaralus.apps.rogue.generators;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Area;

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

    public List<Area> bspSplitArea(Area area) {
        List<Area> areas = new ArrayList<>();

        for (Area subArea : splitByY(area.getCells(), 2))
            areas.addAll(splitByX(subArea.getCells(), 2));

        return areas;
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
