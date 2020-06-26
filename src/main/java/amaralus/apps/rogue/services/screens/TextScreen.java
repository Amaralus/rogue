package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.TextScreenDrawer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static javafx.scene.input.KeyCode.ESCAPE;

public class TextScreen extends Screen {

    private Supplier<List<String>> textListSupplier;
    private String header;

    public TextScreen(Screen previousScreen, String header, Supplier<List<String>> textListSupplier) {
        this.textListSupplier = textListSupplier;
        this.header = header;

        screenDrawer = new TextScreenDrawer(this);

        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(previousScreen)));
    }

    public TextScreen(Screen previousScreen, String header, List<String> textsList) {
        this(previousScreen, header, () -> textsList);
    }

    public TextScreen(Screen previousScreen, String header,  String... texts) {
        this(previousScreen, header, Arrays.asList(texts));
    }

    public List<String> getTextsList() {
        return textListSupplier.get();
    }

    public String getHeader() {
        return header;
    }
}
