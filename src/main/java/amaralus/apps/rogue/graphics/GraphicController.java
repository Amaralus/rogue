package amaralus.apps.rogue.graphics;

import amaralus.apps.rogue.GameController;
import amaralus.apps.rogue.field.Cell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GraphicController {

    private final GameController gameController;

    private final Font font = new Font("Courier New", 16);
    private final Color defaultColor = Color.rgb(243, 243, 243);

    public GraphicController(GameController gameController) {
        this.gameController = gameController;
    }

    public void draw() {
        List<Text> textList = new ArrayList<>(30);

        for (List<Cell> cellLine : gameController.getGameField().getCellLines()) {
            StringBuilder builder = new StringBuilder();

            cellLine.forEach(cell -> builder.append(cell.getEntitySymbol().getSymbol()));
            builder.append('\n');

            textList.add(createText(builder.toString(), defaultColor));
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
