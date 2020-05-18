package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.graphics.drawers.MenuScreenDrawer;
import amaralus.apps.rogue.services.KeyHandler;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;
import amaralus.apps.rogue.services.menu.MenuList;
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyCode.ENTER;

public class MenuScreen extends Screen {

    private GameScreen gameScreen;

    private KeyHandler keyHandler;

    private final MenuList menuList;

    public MenuScreen() {
        screenDrawer = new MenuScreenDrawer(this);
        keyHandler = new KeyHandler();

        menuList = new MenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(gameScreen)),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );

        keyHandler.addKeyAction(ESCAPE, () -> setActiveScreen(gameScreen));
        keyHandler.addKeyAction(UP, menuList::shiftToPrevious);
        keyHandler.addKeyAction(DOWN, menuList::shiftToNext);
        keyHandler.addKeyAction(ENTER, () -> menuList.current().performAction());
    }

    @Override
    public void handleInput(KeyCode keyCode) {
        keyHandler.handleKey(keyCode);
    }

    @Override
    public void update() {
        return;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public MenuList getMenuList() {
        return menuList;
    }
}
