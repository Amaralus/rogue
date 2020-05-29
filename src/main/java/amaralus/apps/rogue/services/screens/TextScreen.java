package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.TextScreenDrawer;

import java.util.Arrays;
import java.util.List;

import static javafx.scene.input.KeyCode.ESCAPE;

public class TextScreen extends Screen {

    private List<String> textsList;

    public TextScreen(Screen previousScreen, List<String> textsList) {
        this.textsList = textsList;

        screenDrawer = new TextScreenDrawer(this);

        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(previousScreen)));
    }

    public TextScreen(Screen previousScreen, String... texts) {
        this(previousScreen, Arrays.asList(texts));
    }

    public List<String> getTextsList() {
        return textsList;
    }
}
