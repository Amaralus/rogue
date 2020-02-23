package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.DefaultComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.entities.world.CellType.EMPTY;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private static final int EXPLORE_RADIUS = 4;

    private Screen gameMenuScreen;
    private Level level;
    private Unit player;

    private boolean warFogEnabled = true;
    private Set<Cell> exploredCells = new HashSet<>();

    public GameScreen() {
        setUpKeyAction();

        level = ServiceLocator.levelGenerator().generateLevel();
        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));
        level.setUpUnitToRandRoom(player);
    }

    private void setUpKeyAction() {
        addKeyAction(F, () -> warFogEnabled = !warFogEnabled);

        addKeyAction(ESCAPE, () -> setActive(gameMenuScreen));
        addKeyAction(UP, () -> player.move(Direction.TOP));
        addKeyAction(DOWN, () -> player.move(Direction.BOTTOM));
        addKeyAction(RIGHT, () -> player.move(Direction.RIGHT));
        addKeyAction(LEFT, () -> player.move(Direction.LEFT));
        addKeyAction(SPACE, () -> {
            level.destroy();
            level = ServiceLocator.levelGenerator().generateLevel();
            level.setUpUnitToRandRoom(player);
        });
    }

    @Override
    public void draw() {
        resetVisibleForPlayer();
        exploredCells = exploreCells();
        List<Text> textList = new ArrayList<>(30);

        textList.add(createPlainText("[Esc] - Меню игры, [Space] - Перегенерировать уровень\n"));

        for (List<Cell> cellLine : level.getGameField().getCells()) {
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

        textList.add(createPlainText("[F] - Включить/выключить туман войны\n"));

        updateTexts(textList);
    }

    private GraphicsComponent actualGraphicsComponent(Cell cell) {
        if (!cell.isExplored() && warFogEnabled) {
            return DefaultComponentsPool.EMPTY_CELL;
        }

        if (cell.containsUnit())
            return cell.getUnit().getGraphicsComponent();
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

    private Set<Cell> exploreCells() {
        int x = player.getPosition().x() - (EXPLORE_RADIUS - 1);
        int y = player.getPosition().y() - (EXPLORE_RADIUS - 1);
        int width = EXPLORE_RADIUS * 2 - 1;
        int height = EXPLORE_RADIUS * 2 - 1;

        if (x < 0) {
            width += x;
            x = 0;
        }
        if (y < 0) {
            height += y;
            y = 0;
        }

        if (x + width > level.getGameField().getWidth())
            width -= ((x + width) - level.getGameField().getWidth());

        if (y + height > level.getGameField().getHeight())
            height -= ((y + height) - level.getGameField().getHeight());

        Set<Cell> cellSet = level.getGameField().subArea(x, y, width, height).getCells().stream()
                .flatMap(List::stream)
                .filter(cell -> cell.getType() != EMPTY)
                .collect(Collectors.toSet());

        for (Cell cell : cellSet) {
            if (!cell.isExplored())
                cell.setExplored(true);
            cell.setVisibleForPlayer(true);
        }
        return cellSet;

    }

    private void resetVisibleForPlayer() {
        for (Cell cell : exploredCells) cell.setVisibleForPlayer(false);
    }

    public void setGameMenuScreen(Screen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }
}
