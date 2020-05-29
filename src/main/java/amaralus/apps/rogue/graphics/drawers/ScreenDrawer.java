package amaralus.apps.rogue.graphics.drawers;

import amaralus.apps.rogue.graphics.Palette;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public abstract class ScreenDrawer {

    private final Font font = new Font("Courier New", 16);

    public abstract void draw();

    protected Text createText(String string, Color color) {
        Text text = new Text(string);
        text.setFont(font);
        text.setFill(color);
        return text;
    }

    protected Text createPlainText(String string) {
        return createText(string, Palette.WHITE_GRAY);
    }

    protected void updateTexts(List<Text> textList) {
        ServiceLocator.gameController().updateTexts(textList);
    }
}
