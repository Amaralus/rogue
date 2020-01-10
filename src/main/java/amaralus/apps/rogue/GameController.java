package amaralus.apps.rogue;

import amaralus.apps.rogue.entities.Unit;
import amaralus.apps.rogue.entities.field.GameField;
import amaralus.apps.rogue.generators.RoomGenerator;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.GraphicsController;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

import static amaralus.apps.rogue.graphics.EntitySymbol.*;
import static amaralus.apps.rogue.graphics.Palette.*;

public class GameController {

    private final MainApplication application;
    private final GraphicsController graphicsController;

    private Unit player;

    private boolean handleEvents = true;

    private GameField gameField;

    public GameController(MainApplication application) {
        this.application = application;
        graphicsController = new GraphicsController(this);

        application.getScene().setOnKeyPressed(event -> handleKeyEvent(event.getCode()));

        gameField = new GameField(120, 30);

        new RoomGenerator().generate(gameField, 10, 6, 54, 17);

        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

        gameField.addUnit(player,58, 20);
        gameField.addUnit(new Unit(new GraphicsComponent(HEART, RED)), 56, 19);

        graphicsController.draw();
    }

    public void handleKeyEvent(KeyCode key) {
        if (!handleEvents)
            return;

        switch (key) {
            case UP:
                player.moveTop();
                break;
            case DOWN:
                player.moveBottom();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case LEFT:
                player.moveLeft();
                break;
        }

        graphicsController.draw();
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
