package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.services.GameController;
import amaralus.apps.rogue.services.io.FileService;
import amaralus.apps.rogue.services.menu.MenuElement;

import static amaralus.apps.rogue.services.ServiceLocator.getService;
import static amaralus.apps.rogue.services.ServiceLocator.serviceLocator;


public class GameMenuScreen extends MenuScreen {

    private TextScreen controlsScreen;

    public GameMenuScreen() {
        super("Меню: [\u2191], [\u2193] - смещение [Enter] - выбор");
        serviceLocator().register(this);

        controlsScreen = new TextScreen(
                this,
                "Управление",
                getService(FileService.class).loadFileAsLinesFromResources("controls.txt")
        );

        setUpMenuList();
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(getService(GameScreen.class))),
                new MenuElement("Управление", () -> setActiveScreen(controlsScreen)),
                new MenuElement("Выйти из игры", () -> getService(GameController.class).exitGame())
        );
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(getService(GameScreen.class)));
    }
}
