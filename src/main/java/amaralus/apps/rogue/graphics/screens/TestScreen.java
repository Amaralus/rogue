package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.graphics.Palette;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Collections;

import static amaralus.apps.rogue.ServiceLocator.*;

public class TestScreen extends Screen {

    private Screen gameScreen;

    @Override
    public void draw() {
        Text text = graphicsController().createText("TEST SCREEN! PRESS ESC TO RETURN!", Palette.WHITE_GRAY);
        gameController().updateTexts(Collections.singletonList(text));
    }

    @Override
    protected void handleKey(KeyCode key) {
        if (key == KeyCode.ESCAPE) setActive(gameScreen);
    }

    public void setGameScreen(Screen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
