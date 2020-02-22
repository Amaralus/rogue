package amaralus.apps.rogue.graphics.screens;

import amaralus.apps.rogue.services.menu.MenuElement;
import amaralus.apps.rogue.services.menu.MenuList;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static amaralus.apps.rogue.graphics.Palette.WHITE_GRAY;
import static amaralus.apps.rogue.services.ServiceLocator.*;
import static javafx.scene.input.KeyCode.*;

public class MenuScreen extends Screen {

    private Screen gameScreen;
    private final MenuList menuList;

    public MenuScreen() {
        menuList = new MenuList(
                new MenuElement("Продолжить", () -> setActive(gameScreen)),
                new MenuElement("Выйти на рабочий стол", () -> gameController().exitGame())
        );

        addKeyAction(ESCAPE, () -> setActive(gameScreen));
        addKeyAction(UP, menuList::shiftToPrevious);
        addKeyAction(DOWN, menuList::shiftToNext);
        addKeyAction(ENTER, () -> menuList.current().performAction());
    }

    @Override
    public void draw() {
        List<Text> menuTexts = new ArrayList<>(menuList.getElementList().size() + 1);

        menuTexts.add(graphicsController().createText("Пауза, используй [\u2191] и [\u2193] для смещения, [Enter] для выбора\n\n", WHITE_GRAY));

        menuTexts.addAll(menuList.getElementList().stream()
                .map(menuElement -> menuElement.equals(menuList.current()) ? " -> " + menuElement.getText() : "    " + menuElement.getText())
                .map(menuText -> graphicsController().createText(menuText + "\n", WHITE_GRAY))
                .collect(Collectors.toList()));

        gameController().updateTexts(menuTexts);
    }

    public void setGameScreen(Screen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
