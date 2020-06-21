package amaralus.apps.rogue.graphics.drawers;

import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.services.ExplorationService;
import amaralus.apps.rogue.services.screens.GameScreen;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GameScreenDrawer extends ScreenDrawer {

    private GameScreen gameScreen;
    private ExplorationService explorationService;

    private boolean warFogEnabled = true;
    private List<Cell> visibleCells = new ArrayList<>();

    public GameScreenDrawer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        explorationService = new ExplorationService();
    }

    @Override
    public void draw() {
        updateVisibleCells();

        List<Text> textList = new ArrayList<>(30);

        textList.add(createPlainText(" [Esc] - Меню\n"));

        for (List<Cell> cellLine : gameScreen.getGamePlayService().getLevel().getGameField().getCells()) {
            StringBuilder builder = new StringBuilder();

            // текущий цвет для определения новых цветов
            Color currentColor = actualColor(actualGraphicsComponent(cellLine.get(0)), cellLine.get(0).isVisibleForPlayer());
            for (Cell cell : cellLine) {
                GraphicsComponent grComponent = actualGraphicsComponent(cell);

                // если новый цвет отличается
                if (currentColor != actualColor(grComponent, cell.isVisibleForPlayer())) {
                    //добавляем собраны текст к списку, обновляем текущий цвет и начинаем собирать новый текст
                    textList.add(createText(builder.toString(), currentColor));
                    currentColor = actualColor(grComponent, cell.isVisibleForPlayer());
                    builder = new StringBuilder();
                }

                builder.append(grComponent.getEntitySymbol().getChar());
            }
            // в конце линии всегда перенос сроки
            builder.append('\n');

            textList.add(createText(builder.toString(), currentColor));
        }

        textList.add(createPlainText(" Золото: " + getGoldCount() + " Уровень: " + gameScreen.getGamePlayService().getLevelNumber() + "\n"));

        updateTexts(textList);
    }

    private int getGoldCount() {
        Item gold = gameScreen.getGamePlayService().getPlayer().getInventory().getItemById(1);
        return gold == null ? 0 : gold.count();
    }

    private GraphicsComponent actualGraphicsComponent(Cell cell) {
        if (!cell.isExplored() && warFogEnabled) {
            return GraphicsComponentsPool.EMPTY_CELL;
        }

        if (cell.containsUnit()) {
            if (cell.getUnit().equals(gameScreen.getGamePlayService().getPlayer()))
                return cell.getUnit().getGraphicsComponent();
            else if (cell.isVisibleForPlayer() || !warFogEnabled)
                return cell.getUnit().getGraphicsComponent();
            else return cell.getGraphicsComponent();
        }


        if (cell.containsItem() && (cell.isVisibleForPlayer() || !warFogEnabled))
            return cell.getItem().getGraphicsComponent();
        else
            return cell.getGraphicsComponent();
    }

    private Color actualColor(GraphicsComponent graphicsComponent, boolean visibleForPlayer) {
        Color color;
        if (warFogEnabled) {
            if (visibleForPlayer)
                color = graphicsComponent.getColor();
            else
                color = graphicsComponent.getWarFogColor();
        } else
            color = graphicsComponent.getColor();

        return color == null ? graphicsComponent.getColor() : color;
    }

    private void updateVisibleCells() {
        for (Cell cell : visibleCells) cell.setVisibleForPlayer(false);

        visibleCells = explorationService.getVisibleCells2(gameScreen.getGamePlayService().getPlayer());

        for (Cell cell : visibleCells) {
            if (!cell.isExplored())
                cell.setExplored(true);
            cell.setVisibleForPlayer(true);
        }
    }

    public void swapWarFogEnabled() {
        warFogEnabled = !warFogEnabled;
    }
}
