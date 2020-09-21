package amaralus.apps.rogue.services;

import amaralus.apps.rogue.MainApplication;
import amaralus.apps.rogue.entities.items.ItemFactory;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.graphics.ExplorationService;
import amaralus.apps.rogue.services.game.EventJournal;
import amaralus.apps.rogue.services.io.FileService;
import amaralus.apps.rogue.services.screens.GameScreen;
import amaralus.apps.rogue.services.screens.GameMenuScreen;
import amaralus.apps.rogue.services.screens.Screen;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;
import static amaralus.apps.rogue.services.screens.Screen.activeScreen;

public class GameController {

    private final MainApplication application;
    private boolean keyHandling = true;

    public GameController(MainApplication application) {
        this.application = application;

        serviceLocator().register(this);
        serviceLocator().register(new FileService());
        serviceLocator().register(new LevelGenerator());
        serviceLocator().register(new EventJournal());
        serviceLocator().register(new ExplorationService());
        new ItemFactory();

        application.getScene().setOnKeyPressed(event -> gameLoop(event.getCode()));

        try {
            Screen.setActiveScreen(new GameScreen());
            new GameMenuScreen();

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
