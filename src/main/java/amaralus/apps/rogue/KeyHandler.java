package amaralus.apps.rogue;

import javafx.scene.input.KeyCode;

public abstract class KeyHandler {

    private boolean keyHandling = true;

    public void handleKeyEvent(KeyCode key) {
        if (keyHandlingEnabled()) handleKey(key);
    }

    protected abstract void handleKey(KeyCode key);

    public void enableKeyHandling(boolean value) {
        keyHandling = value;
    }

    public boolean keyHandlingEnabled() {
        return keyHandling;
    }
}
