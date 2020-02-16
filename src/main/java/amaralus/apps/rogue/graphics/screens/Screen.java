package amaralus.apps.rogue.graphics.screens;

import javafx.scene.input.KeyCode;

public abstract class Screen {

    private static Screen activeScreen;

    public static Screen getActive() {
        return activeScreen;
    }

    public static void setActive(Screen activeScreen) {
        Screen.activeScreen = activeScreen;
    }

    public abstract void handleEvent(KeyCode key);

    public abstract void draw();
}
