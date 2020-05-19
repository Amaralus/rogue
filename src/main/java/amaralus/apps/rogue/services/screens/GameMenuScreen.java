package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;

import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;


public class GameMenuScreen extends MenuScreen {

    public GameMenuScreen() {
        super("Меню, используй [\u2191] и [\u2193] для смещения, [Enter] для выбора");
        ServiceLocator.register(this);

        setUpMenuList();
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(gameScreen())),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(gameScreen()));
    }
}
