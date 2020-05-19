package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.graphics.drawers.MenuScreenDrawer;
import amaralus.apps.rogue.services.menu.MenuList;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyCode.ENTER;

public abstract class MenuScreen extends Screen {

    protected MenuList menuList = new MenuList();
    private String menuTitle;

    public MenuScreen(String menuTitle) {
        this.menuTitle = menuTitle;
        screenDrawer = new MenuScreenDrawer(this);

        commandPool.put(ESCAPE, returnToPreviousScreenCommand());
        commandPool.put(UP, new Command<>(menuList::shiftToPrevious));
        commandPool.put(DOWN, new Command<>(menuList::shiftToNext));
        commandPool.put(ENTER, new Command<>(() -> menuList.current().executeComand()));
    }

    @Override
    public void update() {
        inputCommand.execute();
        inputCommand = Command.NULLABLE_COM;
    }

    protected abstract void setUpMenuList();

    protected abstract Command<Object> returnToPreviousScreenCommand();

    public MenuList getMenuList() {
        return menuList;
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
