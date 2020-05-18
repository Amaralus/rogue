package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.services.KeyHandler;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;
import amaralus.apps.rogue.services.menu.MenuList;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.*;

public class MenuScreen extends Screen {

    private Screen gameScreen;
    private final MenuList menuList;

    private KeyHandler keyHandler;

    public MenuScreen() {
        keyHandler = new KeyHandler();

        menuList = new MenuList(
                new MenuElement("Продолжить", () -> setActive(gameScreen)),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );

        keyHandler.addKeyAction(ESCAPE, () -> setActive(gameScreen));
        keyHandler.addKeyAction(UP, menuList::shiftToPrevious);
        keyHandler.addKeyAction(DOWN, menuList::shiftToNext);
        keyHandler.addKeyAction(ENTER, () -> menuList.current().performAction());
    }

    @Override
    public void handleKey(KeyCode keyCode) {
        keyHandler.handleKey(keyCode);
    }

    @Override
    public void draw() {
        List<Text> menuTexts = new ArrayList<>(menuList.getElementList().size() + 1);

        menuTexts.add(createPlainText("Меню, используй [\u2191] и [\u2193] для смещения, [Enter] для выбора\n\n"));

        menuTexts.addAll(menuList.getElementList().stream()
                .map(menuElement -> menuElement.equals(menuList.current()) ? " -> " + menuElement.getText() : "    " + menuElement.getText())
                .map(menuText -> createPlainText(menuText + "\n"))
                .collect(Collectors.toList()));

        updateTexts(menuTexts);
    }

    public void setGameScreen(Screen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
}
