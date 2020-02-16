package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.KeyHandler;

public abstract class Screen extends KeyHandler {

    private static Screen activeScreen;

    public static Screen getActive() {
        return activeScreen;
    }

    public static void setActive(Screen activeScreen) {
        Screen.activeScreen = activeScreen;
    }

    public abstract void draw();
}
