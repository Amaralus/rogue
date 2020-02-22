package amaralus.apps.rogue.services;

import amaralus.apps.rogue.MainApplication;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.graphics.GraphicsController;
import amaralus.apps.rogue.graphics.screens.GameScreen;
import amaralus.apps.rogue.graphics.screens.Screen;
import amaralus.apps.rogue.graphics.screens.MenuScreen;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

public class GameController extends KeyHandler {

    private final MainApplication application;

    public GameController(MainApplication application) {
        this.application = application;

        ServiceLocator.provide(this);
        ServiceLocator.provide(new GraphicsController());
        ServiceLocator.provide(new LevelGenerator());

        application.getScene().setOnKeyPressed(event -> handleKey(event.getCode()));

        try {
            GameScreen gameScreen = new GameScreen();
            MenuScreen gameMenuScreen = new MenuScreen();

            gameScreen.setGameMenuScreen(gameMenuScreen);
            gameMenuScreen.setGameScreen(gameScreen);

            Screen.setActive(gameScreen);

            Screen.getActive().draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void handleKey(KeyCode key) {
        if (!keyHandlingEnabled()) return;

        try {
            Screen.getActive().handleKey(key);

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
        enableKeyHandling(false);
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
