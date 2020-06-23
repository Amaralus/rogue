package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;

import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;


public class GameMenuScreen extends MenuScreen {

    private TextScreen controlsScreen;

    public GameMenuScreen() {
        super("Меню: [\u2191], [\u2193] - смещение [Enter] - выбор");
        ServiceLocator.register(this);

        controlsScreen = new TextScreen(
                this,
                "Управление",
                "Игра:",
                "[\u2190] [\u2191] [\u2192] [\u2193] - Перемещение",
                "[T] - Подобрать предмет (стоя на нём)",
                "[I] - Инвентарь",
                "[F] - Переключить туман войны",
                "[E] - Взаимодействовать",
                "[J] - Журнал событий",
                "[SPACE] - Перегенерировать уровень\n",
                "Инвентарь:",
                "[D] - Выкинуть предмет"
        );

        setUpMenuList();
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Продолжить", () -> setActiveScreen(gameScreen())),
                new MenuElement("Управление", () -> setActiveScreen(controlsScreen)),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(gameScreen()));
    }
}
