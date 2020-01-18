package amaralus.apps.rogue;

import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.generators.RandomGenerator;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.GraphicsController;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.List;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;

public class GameController {

    private final MainApplication application;
    private final GraphicsController graphicsController;

    private Unit player;

    private boolean handleEvents = true;

    private Level level;

    public GameController(MainApplication application) {
        this.application = application;
        graphicsController = new GraphicsController(this);
        application.getScene().setOnKeyPressed(event -> handleKeyEvent(event.getCode()));

        try {
            level = new LevelGenerator().generateLevel();

            player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

            level.addUnit(player, RandomGenerator.randElement(level.getRooms()).getRandCellPosition());

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
                    player.move(TOP);
                    break;
                case DOWN:
                    player.move(BOTTOM);
                    break;
                case RIGHT:
                    player.move(RIGHT);
                    break;
                case LEFT:
                    player.move(LEFT);
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

    public Level getLevel() {
        return level;
    }
}
