package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.entities.units.Unit;
import amaralus.apps.rogue.entities.world.Level;
import amaralus.apps.rogue.graphics.GraphicsComponent;
import javafx.scene.input.KeyCode;

import static amaralus.apps.rogue.ServiceLocator.*;
import static amaralus.apps.rogue.entities.Direction.*;
import static amaralus.apps.rogue.graphics.EntitySymbol.SMILING_FACE;
import static amaralus.apps.rogue.graphics.Palette.YELLOW;

public class GameScreen extends Screen {

    private Screen testScreen;
    private Level level;
    private Unit player;

    public GameScreen() {
        level = levelGenerator().generateLevel();

        player = new Unit(new GraphicsComponent(SMILING_FACE, YELLOW));

        level.setUpUnitToRandRoom(player);
    }

    @Override
    public void handleKey(KeyCode key) {
        switch (key) {
            case ESCAPE:
                gameController().exitGame();
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
                level = levelGenerator().generateLevel();
                level.setUpUnitToRandRoom(player);
                break;
            case T:
                setActive(testScreen);
                break;
        }
    }

    @Override
    public void draw() {
        graphicsController().draw(level);
    }

    public void setTestScreen(Screen testScreen) {
        this.testScreen = testScreen;
    }
}
