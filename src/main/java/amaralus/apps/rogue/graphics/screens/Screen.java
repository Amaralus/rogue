package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.graphics.Palette;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public abstract class Screen {

    private static Screen activeScreen;

    private final Font font = new Font("Courier New", 16);

    public static Screen getActive() {
        return activeScreen;
    }

    public static void setActive(Screen activeScreen) {
        Screen.activeScreen = activeScreen;
    }

    public abstract void draw();

    public abstract void handleKey(KeyCode keyCode);

    protected Text createText(String string, Color color) {
        Text text = new Text(string);
        text.setFont(font);
        text.setFill(color);
        return text;
    }

    protected Text createPlainText(String string) {
        return  createText(string, Palette.WHITE_GRAY);
    }

    protected void updateTexts(List<Text> textList) {
        ServiceLocator.gameController().updateTexts(textList);
    }
}
