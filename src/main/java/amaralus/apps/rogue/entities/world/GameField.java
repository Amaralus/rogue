package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameField {

    private final Area area;

    public GameField(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException(
                    String.format("width or height must be greater than zero, but was: width=%d height=%d", width, height));

        List<List<Cell>> cellLines = new ArrayList<>(height);

        for (int y = 0; y < height; y++) {
            List<Cell> cellLine = createCellLine(y, width);
            cellLines.add(cellLine);

            if (y > 0) connectCellLines(cellLines.get(y - 1), cellLine);
        }
        area = new Area(cellLines);
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

    public Cell getCell(int x, int y) {
        return area.getCell(x, y);
    }

    public List<List<Cell>> getCellLines() {
        return area.getCells();
    }

    public void addUnit(Unit entity, Position position) {
        Cell cell = area.getCell(position);
        if (cell.isCanWalk() && cell.notContainsUnit()) {
            cell.setUnit(entity);
            entity.setCurrentCell(cell);
        }
    }
}
