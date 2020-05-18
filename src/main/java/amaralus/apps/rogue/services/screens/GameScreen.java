package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.entities.Direction;
import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import amaralus.apps.rogue.graphics.drawers.GameScreenDrawer;
import amaralus.apps.rogue.services.KeyHandler;
import amaralus.apps.rogue.services.ServiceLocator;
import javafx.scene.input.KeyCode;

import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;
import static javafx.scene.input.KeyCode.*;

public class GameScreen extends Screen {

    private KeyHandler keyHandler = new KeyHandler();

    private MenuScreen gameMenuScreen;

    private Level level;
    private Unit player;

    public GameScreen() {
        screenDrawer = new GameScreenDrawer(this);

        setUpKeyAction();

        level = ServiceLocator.levelGenerator().generateLevel();
        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));
        player.setVisibleRadius(3);
        level.setUpUnitToRandRoom(player);
    }

    private void setUpKeyAction() {
        keyHandler.addKeyAction(F, ((GameScreenDrawer) screenDrawer)::swapWarFogEnabled);

        keyHandler.addKeyAction(ESCAPE, () -> setActiveScreen(gameMenuScreen));
        keyHandler.addKeyAction(UP, () -> player.move(Direction.TOP));
        keyHandler.addKeyAction(DOWN, () -> player.move(Direction.BOTTOM));
        keyHandler.addKeyAction(RIGHT, () -> player.move(Direction.RIGHT));
        keyHandler.addKeyAction(LEFT, () -> player.move(Direction.LEFT));
        keyHandler.addKeyAction(SPACE, () -> {
            level.destroy();
            level = ServiceLocator.levelGenerator().generateLevel();
            level.setUpUnitToRandRoom(player);
        });
    }

    @Override
    public void handleInput(KeyCode keyCode) {
        keyHandler.handleKey(keyCode);
    }

    @Override
    public void update() {
        return;
    }

    public void setGameMenuScreen(MenuScreen gameMenuScreen) {
        this.gameMenuScreen = gameMenuScreen;
    }

    public Level getLevel() {
        return level;
    }

    public Unit getPlayer() {
        return player;
    }
}
