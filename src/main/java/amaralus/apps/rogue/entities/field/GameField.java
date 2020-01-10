package amaralus.apps.rogue.entities.field;

import amaralus.apps.rogue.entities.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameField {

    private final List<List<Cell>> cellLines;

    public GameField(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException(
                    String.format("width or height must be greater than zero, but was: width=%d height=%d", width, height));

        cellLines = new ArrayList<>(height);

        for (int i = 0; i < height; i++) {
            List<Cell> cellLine = createCellLine(width);
            cellLines.add(cellLine);

            if (i > 0) connectCellLines(cellLines.get(i - 1), cellLine);
        }
    }

    private List<Cell> createCellLine(int width) {
        List<Cell> cellLine = new ArrayList<>(width);

        for (int i = 0; i < width; i++) {
            Cell cell = new Cell();
            cellLine.add(cell);

            if (i > 0) {
                Cell prevCell = cellLine.get(i - 1);
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
        return cellLines.get(y).get(x);
    }

    public List<List<Cell>> getCellLines() {
        return cellLines;
    }

    public void addUnit(Unit entity, int x, int y) {
        Cell cell = getCell(x, y);
        if (cell.isCanWalk() && cell.notContainsUnit()) {
            cell.setUnit(entity);
            entity.setCurrentCell(cell);
        }
    }
}
