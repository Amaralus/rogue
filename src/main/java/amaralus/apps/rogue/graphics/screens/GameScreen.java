package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.ServiceLocator;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import javafx.scene.input.KeyCode;

import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;

public class GameScreen extends Screen {

    private Level level;
    private Unit player;

    public GameScreen() {
        level = ServiceLocator.getLevelGenerator().generateLevel();

        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

        level.setUpUnitToRandRoom(player);
    }

    @Override
    public void handleKey(KeyCode key) {
        switch (key) {
            case ESCAPE:
                ServiceLocator.getGameController().exitGame();
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
                level = ServiceLocator.getLevelGenerator().generateLevel();
                level.setUpUnitToRandRoom(player);
                break;
        }
    }

    @Override
    public void draw() {
        ServiceLocator.getGraphicsController().draw(level);
    }
}
