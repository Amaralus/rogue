package amaralus.apps.rogue.graphics;

import amaralus.apps.rogue.ServiceLocator;
import amaralus.apps.rogue.entities.world.Cell;
import amaralus.apps.rogue.entities.world.Level;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GraphicsController {

    private final Font font = new Font("Courier New", 16);

    public void draw(Level level) {
        List<Text> textList = new ArrayList<>(30);

        textList.add(createText("TEST! TOP PANEL! TEST! PRESS T TO CHANGE SCREEN!\n", Palette.WHITE_GRAY));
        for (List<Cell> cellLine : level.getGameField().getCells()) {
            StringBuilder builder = new StringBuilder();

            // текущий цвет для определения новых цветов
            Color currentColor = cellLine.get(0).getActualGraphicsComponent().getColor();
            for (Cell cell : cellLine) {
                GraphicsComponent grComponent = cell.getActualGraphicsComponent();

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

        textList.add(createText("TEST! BOTTOM PANEL! TEST! PRESS T TO CHANGE SCREEN!\n", Palette.WHITE_GRAY));

        ServiceLocator.gameController().updateTexts(textList);
    }

    public Text createText(String string, Color color) {
        Text text = new Text(string);
        text.setFont(font);
        text.setFill(color);
        return text;
    }
}
