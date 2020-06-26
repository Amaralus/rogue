package amaralus.apps.rogue.graphics.drawers;

import amaralus.apps.rogue.services.screens.TextScreen;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TextScreenDrawer extends ScreenDrawer {

    private TextScreen textScreen;

    public TextScreenDrawer(TextScreen textScreen) {
        this.textScreen = textScreen;
    }

    @Override
    public void draw() {
        List<Text> textList = new ArrayList<>();

        textList.add(createPlainText(" " + textScreen.getHeader() + "\n\n"));

        for (String text : textScreen.getTextsList())
            textList.add(createPlainText(" " + text + "\n"));

        updateTexts(textList);
    }
}
