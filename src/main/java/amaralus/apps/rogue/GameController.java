package amaralus.apps.rogue;

import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.graphics.GraphicsController;
import amaralus.apps.rogue.graphics.screens.GameScreen;
import amaralus.apps.rogue.graphics.screens.Screen;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

public class GameController {

    private final MainApplication application;
    private final GraphicsController graphicsController;
    private final LevelGenerator levelGenerator;

    private Screen gameScreen;

    private boolean handleEvents = true;

    public GameController(MainApplication application) {
        this.application = application;
        graphicsController = new GraphicsController(this);
        levelGenerator = new LevelGenerator();
        application.getScene().setOnKeyPressed(event -> handleKeyEvent(event.getCode()));

        try {
            gameScreen = new GameScreen(this, graphicsController);
            Screen.setActive(gameScreen);

            Screen.getActive().draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void handleKeyEvent(KeyCode key) {
        if (!handleEvents)
            return;

        try {
            Screen.getActive().handleEvent(key);

            Screen.getActive().draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void updateTexts(List<Text> textList) {
        application.getPane().getChildren().clear();
        application.getPane().getChildren().addAll(textList);
    }

    public void showErrorAndExit(Exception e) {
        handleEvents = false;
        application.showAlert(e);
        exitGame();
    }

    public void exitGame() {
        try {
            application.stop();
        } catch (Exception e) {
            application.showAlert(e);
        }
    }

    public LevelGenerator getLevelGenerator() {
        return levelGenerator;
    }
}
