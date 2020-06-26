package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.services.GameController;
import amaralus.apps.rogue.services.menu.MenuElement;

import static amaralus.apps.rogue.services.ServiceLocator.getService;


public class GameOverScreen extends MenuScreen {

    public GameOverScreen() {
        super();

        menuTitle = getService(GameScreen.class).getGamePlayService().isWin() ? "Победа!" : "Поражение!";

        Item gold = getService(GameScreen.class).getGamePlayService().getPlayer().getInventory().getItemById(1);
        menuTitle += "\n\n Золота собрано: " + (gold == null ? 0 : gold.count());

        setUpMenuList();
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Начать новую игру", () -> {
                    getService(GameScreen.class).getGamePlayService().initGame();
                    setActiveScreen(getService(GameScreen.class));
                }),
                new MenuElement("Выйти из игры", () -> getService(GameController.class).exitGame())
        );
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(getService(GameScreen.class)));
    }
}
