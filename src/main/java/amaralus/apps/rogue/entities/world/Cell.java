package amaralus.apps.rogue.entities.world;

import amaralus.apps.rogue.entities.Destroyable;
import amaralus.apps.rogue.entities.Position;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;

import java.util.Objects;

public class Cell implements Destroyable {

    private final Position position;
    private Cell topCell;
    private Cell bottomCell;
    private Cell rightCell;
    private Cell leftCell;

    private GraphicsComponent graphicsComponent;
    private CellType type;

    private Unit unit;
    private Item item;

    private InteractEntity interactEntity;

    private boolean canWalk = false;
    private boolean explored = false;
    private boolean visibleForPlayer = false;
    private boolean canPutItem = false;

    public Cell(Position position) {
        this.position = position;
        graphicsComponent = GraphicsComponentsPool.EMPTY_CELL;
        type = CellType.EMPTY;
    }

    @Override
    public void destroy() {
        topCell = null;
        bottomCell = null;
        rightCell = null;
        leftCell = null;
        graphicsComponent = null;
        unit = null;
        item = null;
    }

    public Position getPosition() {
        return position;
    }

    // тип клетки

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    // соседние клетки

    public Cell getTopCell() {
        return topCell;
    }

    public Cell getBottomCell() {
        return bottomCell;
    }

    public Cell getRightCell() {
        return rightCell;
    }

    public Cell getLeftCell() {
        return leftCell;
    }

    public void setTopCell(Cell topCell) {
        this.topCell = topCell;
    }

    public void setBottomCell(Cell bottomCell) {
        this.bottomCell = bottomCell;
    }

    public void setRightCell(Cell rightCell) {
        this.rightCell = rightCell;
    }

    public void setLeftCell(Cell leftCell) {
        this.leftCell = leftCell;
    }

    public boolean topCellExist() {
        return topCell != null;
    }

    public boolean topCellNotExist() {
        return !topCellExist();
    }

    public boolean bottomCellExist() {
        return bottomCell != null;
    }

    public boolean bottomCellNotExist() {
        return !bottomCellExist();
    }

    public boolean rightCellExist() {
        return rightCell != null;
    }

    public boolean rightCellNotExist() {
        return !rightCellExist();
    }

    public boolean leftCellExist() {
        return leftCell != null;
    }

    public boolean leftCellNotExist() {
        return !leftCellExist();
    }

    // графика

    public void setGraphicsComponent(GraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isVisibleForPlayer() {
        return visibleForPlayer;
    }

    public void setVisibleForPlayer(boolean visibleForPlayer) {
        this.visibleForPlayer = visibleForPlayer;
    }

    // сущность

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean containsUnit() {
        return unit != null;
    }

    public boolean notContainsUnit() {
        return !containsUnit();
    }

    // Предметы

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean containsItem() {
        return item != null;
    }

    public boolean notContainsItem() {
        return !containsItem();
    }

    public boolean isCanPutItem() {
        return canPutItem;
    }

    public void setCanPutItem(boolean canPutItem) {
        this.canPutItem = canPutItem;
    }

    // возможность пройти

    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }

    public boolean isCanWalk() {
        return canWalk;
    }

    // Взаимодействие с клеткой


    public InteractEntity getInteractEntity() {
        return interactEntity;
    }

    public void setInteractEntity(InteractEntity interactEntity) {
        this.interactEntity = interactEntity;
    }

    public void interact() {
        if (interactEntity != null)
            interactEntity.interact();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return canWalk == cell.canWalk &&
                explored == cell.explored &&
                visibleForPlayer == cell.visibleForPlayer &&
                Objects.equals(position, cell.position) &&
                Objects.equals(graphicsComponent, cell.graphicsComponent) &&
                type == cell.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, graphicsComponent, type, canWalk, explored, visibleForPlayer);
    }
}
