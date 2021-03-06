package amaralus.apps.rogue.graphics.drawers;

import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.Palette;
import amaralus.apps.rogue.services.game.EventJournal;
import amaralus.apps.rogue.graphics.ExplorationService;
import amaralus.apps.rogue.services.screens.GameScreen;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static amaralus.apps.rogue.services.ServiceLocator.getService;
import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;

public class GameScreenDrawer extends ScreenDrawer {

    private GameScreen gameScreen;

    private boolean warFogEnabled = true;
    private List<Cell> visibleCells = new ArrayList<>();

    public GameScreenDrawer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void draw() {
        updateVisibleCells();

        List<Text> textList = new ArrayList<>(30);

        textList.add(createPlainText(" " + serviceLocator().get(EventJournal.class).getLastEvent() + "\n"));

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

        textList.addAll(Arrays.asList(
                createPlainText("Уровень: " + gameScreen.getGamePlayService().getLevelNumber()),
                createPlainText("  Здоровье: "),
                playerHealthText(gameScreen.getGamePlayService().getPlayer()),
                createPlainText("  Золото: "),
                createText(getGoldCount() + "", Palette.YELLOW)

        ));

        updateTexts(textList);
    }

    private Text playerHealthText(Unit player) {
        Color color;
        if (player.getHealth() < 70) {
            if (player.getHealth() < 30)
                color = Palette.RED;
            else
                color = Palette.YELLOW;
        } else
            color = Palette.GREEN;

        return createText(player.getHealth() + "", color);
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

        visibleCells = getService(ExplorationService.class).getVisibleCells2(gameScreen.getGamePlayService().getPlayer());

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
