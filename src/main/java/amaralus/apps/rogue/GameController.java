package amaralus.apps.rogue;

import amaralus.apps.rogue.field.GameField;
import amaralus.apps.rogue.graphics.GraphicsController;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

public class GameController {

    private final MainApplication application;
    private final GraphicsController graphicsController;

    private boolean handleEvents = true;

    private GameField gameField;

    public GameController(MainApplication application) {
        this.application = application;
        graphicsController = new GraphicsController(this);

        application.getScene().setOnKeyPressed(event -> handleKeyEvent(event.getCode()));

        gameField = new GameField(120, 30);
        graphicsController.draw();
    }

    public void handleKeyEvent(KeyCode key) {
        if (!handleEvents)
            return;
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

    public GameField getGameField() {
        return gameField;
    }
}
