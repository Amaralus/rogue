package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.GameController;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.GraphicsController;
import javafx.scene.input.KeyCode;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;

public class GameScreen extends Screen {

    private GameController gameController;
    private GraphicsController graphicsController;
    private Level level;
    private Unit player;

    public GameScreen(GameController gameController, GraphicsController graphicsController) {
        this.gameController = gameController;
        this.graphicsController = graphicsController;
        level = gameController.getLevelGenerator().generateLevel();

        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

        level.setUpUnitToRandRoom(player);
    }

    @Override
    public void handleEvent(KeyCode key) {
        switch (key) {
            case ESCAPE:
                gameController.exitGame();
                break;
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
            case SPACE:
                level.destroy();
                level = gameController.getLevelGenerator().generateLevel();
                level.setUpUnitToRandRoom(player);
                break;
        }
    }

    @Override
    public void draw() {
        graphicsController.draw(level);
    }
}
