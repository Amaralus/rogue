package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponentsPool;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.services.ExplorationService;
import amaralus.apps.rogue.services.KeyHandler;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private Screen gameMenuScreen;
    private ExplorationService explorationService;

    private KeyHandler keyHandler;

    private Level level;
    private Unit player;

    private boolean warFogEnabled = true;
    private List<Cell> visibleCells = new ArrayList<>();

    public GameScreen() {
        keyHandler = new KeyHandler();
        setUpKeyAction();

        explorationService = new ExplorationService();

        level = ServiceLocator.levelGenerator().generateLevel();
        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));
        player.setVisibleRadius(3);
        level.setUpUnitToRandRoom(player);
    }

    private void setUpKeyAction() {
        keyHandler.addKeyAction(F, () -> warFogEnabled = !warFogEnabled);

        keyHandler.addKeyAction(ESCAPE, () -> setActive(gameMenuScreen));
        keyHandler.addKeyAction(UP, () -> player.move(Direction.TOP));
        keyHandler.addKeyAction(DOWN, () -> player.move(Direction.BOTTOM));
        keyHandler.addKeyAction(RIGHT, () -> player.move(Direction.RIGHT));
        keyHandler.addKeyAction(LEFT, () -> player.move(Direction.LEFT));
        keyHandler.addKeyAction(SPACE, () -> {
            level.destroy();
            level = ServiceLocator.levelGenerator().generateLevel();
            level.setUpUnitToRandRoom(player);
        });
    }

    @Override
    public void handleKey(KeyCode keyCode) {
        keyHandler.handleKey(keyCode);
    }

    @Override
    public void draw() {
        updateVisibleCells();

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
            return GraphicsComponentsPool.EMPTY_CELL;
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

    private void updateVisibleCells() {
        for (Cell cell : visibleCells) cell.setVisibleForPlayer(false);

        visibleCells = explorationService.getVisibleCells(player);

        for (Cell cell : visibleCells) {
            if (!cell.isExplored())
                cell.setExplored(true);
            cell.setVisibleForPlayer(true);
        }
    }

    public void setGameMenuScreen(Screen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }
}
