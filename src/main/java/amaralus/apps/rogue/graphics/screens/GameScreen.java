package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;

import static amaralus.apps.rogue.services.ServiceLocator.*;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private Screen gameMenuScreen;
    private Level level;
    private Unit player;

    public GameScreen() {
        setUpKeyAction();

        level = levelGenerator().generateLevel();
        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));
        level.setUpUnitToRandRoom(player);
    }

    private void setUpKeyAction() {
        addKeyAction(ESCAPE, () -> setActive(gameMenuScreen));
        addKeyAction(UP, () -> player.move(Direction.TOP));
        addKeyAction(DOWN, () -> player.move(Direction.BOTTOM));
        addKeyAction(RIGHT, () -> player.move(Direction.RIGHT));
        addKeyAction(LEFT, () -> player.move(Direction.LEFT));
        addKeyAction(SPACE, () -> {
            level.destroy();
            level = levelGenerator().generateLevel();
            level.setUpUnitToRandRoom(player);
        });
    }

    @Override
    public void draw() {
        graphicsController().draw(level);
    }

    public void setGameMenuScreen(Screen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }
}
