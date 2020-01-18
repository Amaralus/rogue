package amaralus.apps.rogue;

import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.GameField;
import amaralus.apps.rogue.entities.world.Room;
import amaralus.apps.rogue.generators.RandomGenerator;
import amaralus.apps.rogue.generators.WorldGenerator;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.GraphicsController;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;

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

        try {
            gameField = new GameField(120, 30);

            WorldGenerator worldGenerator = new WorldGenerator(gameField);

            Room room = worldGenerator.generateDungeon();

            player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

            gameField.addUnit(player, RandomGenerator.randPositionFromRoom(room));

            graphicsController.draw();
        } catch (Exception e) {
            showErrorAndExit(e);
        }
    }

    public void handleKeyEvent(KeyCode key) {
        if (!handleEvents)
            return;

        try {
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

    public GameField getGameField() {
        return gameField;
    }
}
