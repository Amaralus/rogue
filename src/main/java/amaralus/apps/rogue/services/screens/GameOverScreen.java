package amaralus.apps.rogue.services.screens;

import amaralus.apps.rogue.commands.Command;
import amaralus.apps.rogue.entities.items.Item;
import amaralus.apps.rogue.services.ServiceLocator;
import amaralus.apps.rogue.services.menu.MenuElement;

import static amaralus.apps.rogue.services.ServiceLocator.gameScreen;

public class GameOverScreen extends MenuScreen {

    public GameOverScreen() {
        super();

        menuTitle = gameScreen().getGamePlayService().isWin() ? "Победа!" : "Поражение!";

        Item gold = gameScreen().getGamePlayService().getPlayer().getInventory().getItemById(1);
        menuTitle += "\n\n Золота собрано: " + (gold == null ? 0 : gold.count());

        setUpMenuList();
    }

    @Override
    protected void setUpMenuList() {
        menuList.setUpMenuList(
                new MenuElement("Начать новую игру", () -> {
                    gameScreen().getGamePlayService().initGame();
                    setActiveScreen(gameScreen());
                }),
                new MenuElement("Выйти из игры", () -> ServiceLocator.gameController().exitGame())
        );
    }

    @Override
    protected Command<Object> returnToPreviousScreenCommand() {
        return new Command<>(() -> setActiveScreen(gameScreen()));
    }
}
