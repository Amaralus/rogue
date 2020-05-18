package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.MenuScreenDrawer;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;
import amaralus.apps.rogue.services.menu.MenuList;

import static javafx.scene.input.KeyCode.*;


public class MenuScreen extends Screen {

    private GameScreen gameScreen;

    private final MenuList menuList;

    public MenuScreen() {
        screenDrawer = new MenuScreenDrawer(this);

        menuList = new MenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(gameScreen)),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );

        commandPool.put(ESCAPE, new Command<>(() -> setActiveScreen(gameScreen)));
        commandPool.put(UP, new Command<>(menuList::shiftToPrevious));
        commandPool.put(DOWN, new Command<>(menuList::shiftToNext));
        commandPool.put(ENTER, new Command<>(() -> menuList.current().executeComand()));
    }

    @Override
    public void update() {
        inputCommand.execute();
        inputCommand = Command.NULLABLE_COM;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public MenuList getMenuList() {
        return menuList;
    }
}
