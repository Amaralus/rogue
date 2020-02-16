package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.graphics.Palette;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Collections;

import static amaralus.apps.rogue.services.ServiceLocator.*;

public class TestScreen extends Screen {

    private Screen gameScreen;

    public TestScreen() {
        addKeyAction(KeyCode.ESCAPE, () -> setActive(gameScreen));
    }

    @Override
    public void draw() {
        Text text = graphicsController().createText("TEST SCREEN! PRESS ESC TO RETURN!", Palette.WHITE_GRAY);
        gameController().updateTexts(Collections.singletonList(text));
    }

    public void setGameScreen(Screen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
