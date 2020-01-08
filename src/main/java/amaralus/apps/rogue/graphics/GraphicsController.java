package amaralus.apps.rogue.graphics;

import amaralus.apps.rogue.GameController;
import amaralus.apps.rogue.field.Cell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static amaralus.apps.rogue.graphics.Palette.BLACK_BLUE;

public class GraphicsController {

    private final GameController gameController;

    private final Font font = new Font("Courier New", 16);

    public GraphicsController(GameController gameController) {
        this.gameController = gameController;
    }

    public void draw() {
        List<Text> textList = new ArrayList<>(30);

        for (List<Cell> cellLine : gameController.getGameField().getCellLines()) {
            StringBuilder builder = new StringBuilder();

            // текущий цвет для определения новых цветов (по умолчанию чёрный)
            Color currentColor = BLACK_BLUE;
            for (Cell aCellLine : cellLine) {
                GraphicsComponent grComponent = aCellLine.getGraphicsComponent();

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

        gameController.updateTexts(textList);
    }

    private Text createText(String string, Color color) {
        Text text = new Text(string);
        text.setFont(font);
        text.setFill(color);
        return text;
    }
}
