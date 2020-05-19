package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.ScreenDrawer;
import javafx.scene.input.KeyCode;

import java.util.EnumMap;

public abstract class Screen {

    private static Screen activeScreen;

    protected ScreenDrawer screenDrawer;
    protected Command<?> inputCommand = Command.NULLABLE_COM;

    protected EnumMap<KeyCode, Command<?>> commandPool = new EnumMap<>(KeyCode.class);

    public static Screen activeScreen() {
        return activeScreen;
    }

    public static void setActiveScreen(Screen activeScreen) {
        Screen.activeScreen = activeScreen;
    }

    public void handleInput(KeyCode keyCode) {
        inputCommand = commandPool.getOrDefault(keyCode, Command.NULLABLE_COM);
    }

    public abstract void update();

    public void draw() {
        screenDrawer.draw();
    }

    public ScreenDrawer getScreenDrawer() {
        return screenDrawer;
    }
}
