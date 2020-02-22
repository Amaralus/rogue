package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private Screen gameMenuScreen;
    private Level level;
    private Unit player;

    public GameScreen() {
        setUpKeyAction();

        level = ServiceLocator.levelGenerator().generateLevel();
        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));
        level.setUpUnitToRandRoom(player);
    }

    private void setUpKeyAction() {
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
        List<Text> textList = new ArrayList<>(30);

        textList.add(createPlainText("Верхняя панель, нажми [Esc] для паузы\n"));

        for (List<Cell> cellLine : level.getGameField().getCells()) {
            StringBuilder builder = new StringBuilder();

            // текущий цвет для определения новых цветов
            Color currentColor = actualGraphicsComponent(cellLine.get(0)).getColor();
            for (Cell cell : cellLine) {
                GraphicsComponent grComponent = actualGraphicsComponent(cell);

                // если новый цвет отличается
                if (currentColor != grComponent.getColor()) {
                    //добавляем собраны текст к списку, обновляем текущий цвет и начинаем собирать новый текст
                    textList.add(createText(builder.toString(), currentColor));
                    currentColor = grComponent.getColor();
                    builder = new StringBuilder();
                }

                builder.append(grComponent.getEntitySymbol().getChar());
            }
            // в конце линии всегда перенос сроки
            builder.append('\n');

            textList.add(createText(builder.toString(), currentColor));
        }

        textList.add(createPlainText("Нижняя панель, нажми [Esc] для паузы\n"));

        updateTexts(textList);
    }

    private GraphicsComponent actualGraphicsComponent(Cell cell) {
        if (cell.containsUnit())
            return cell.getUnit().getGraphicsComponent();
        else
            return cell.getGraphicsComponent();
    }

    public void setGameMenuScreen(Screen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }
}
