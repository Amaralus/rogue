package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.services.GameController;
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
                "Игра:",
                "[\u2190] [\u2191] [\u2192] [\u2193] - Перемещение",
                "[T] - Подобрать предмет (стоя на нём)",
                "[I] - Инвентарь",
                "[E] - Взаимодействовать",
                "[J] - Журнал событий",
                "[S] - Искать вокруг игрока скрытые проходы, ловушки и т.д.",
                "Инвентарь:",
                "[D] - Выкинуть предмет"
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
