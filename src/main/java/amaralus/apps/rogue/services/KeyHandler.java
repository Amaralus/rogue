package amaralus.apps.rogue.services;

import amaralus.apps.rogue.utils.Action;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public abstract class KeyHandler {

    private Map<KeyCode, Action> keyActions = new HashMap<>();
    private boolean keyHandling = true;

    public void handleKey(KeyCode key) {
        if (keyHandlingEnabled()) {
            Action action = keyActions.get(key);
            if (action != null) action.perform();
        }
    }

    public void addKeyAction(KeyCode key, Action action) {
        keyActions.put(key, action);
    }

    public void enableKeyHandling(boolean value) {
        keyHandling = value;
    }

    public boolean keyHandlingEnabled() {
        return keyHandling;
    }
}
