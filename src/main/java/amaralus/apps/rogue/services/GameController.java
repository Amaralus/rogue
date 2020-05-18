package amaralus.apps.rogue.services;

import amaralus.apps.rogue.MainApplication;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.services.screens.GameScreen;
import amaralus.apps.rogue.services.screens.MenuScreen;
import amaralus.apps.rogue.services.screens.Screen;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

import static amaralus.apps.rogue.services.screens.Screen.activeScreen;

public class GameController {

    private final MainApplication application;
    private boolean keyHandling = true;

    public GameController(MainApplication application) {
        this.application = application;

        ServiceLocator.provide(this);
        ServiceLocator.provide(new LevelGenerator());

        application.getScene().setOnKeyPressed(event -> gameLoop(event.getCode()));

        try {
            GameScreen gameScreen = new GameScreen();
            MenuScreen gameMenuScreen = new MenuScreen();

            gameScreen.setGameMenuScreen(gameMenuScreen);
            gameMenuScreen.setGameScreen(gameScreen);

            Screen.setActiveScreen(gameScreen);

            activeScreen().getScreenDrawer().draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void gameLoop(KeyCode key) {
        if (!keyHandling) return;

        try {
            activeScreen().handleInput(key);

            activeScreen().update();

            activeScreen().draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void updateTexts(List<Text> textList) {
        application.getPane().getChildren().clear();
        application.getPane().getChildren().addAll(textList);
    }

    public void showErrorAndExit(Exception e) {
        keyHandling = false;
        e.printStackTrace();
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
