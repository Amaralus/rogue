package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.graphics.drawers.ScreenDrawer;
import javafx.scene.input.KeyCode;

public abstract class Screen {

    private static Screen activeScreen;

    protected ScreenDrawer screenDrawer;

    public static Screen activeScreen() {
        return activeScreen;
    }

    public static void setActiveScreen(Screen activeScreen) {
        Screen.activeScreen = activeScreen;
    }

    public abstract void handleInput(KeyCode keyCode);

    public abstract void update();

    public void draw() {
        screenDrawer.draw();
    }

    public ScreenDrawer getScreenDrawer() {
        return screenDrawer;
    }
}
