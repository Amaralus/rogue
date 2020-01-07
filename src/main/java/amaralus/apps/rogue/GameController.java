package amaralus.apps.rogue;

import javafx.scene.input.KeyCode;

public class GameController {

    private final MainApplication application;

    private boolean handleEvents = true;

    public GameController(MainApplication application) {
        this.application = application;
        application.getScene().setOnKeyPressed(event -> handleKeyEvent(event.getCode()));
    }

    public void handleKeyEvent(KeyCode key) {
        if (!handleEvents)
            return;
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
}
