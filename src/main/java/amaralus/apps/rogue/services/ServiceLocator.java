package amaralus.apps.rogue.services;

import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.services.screens.GameScreen;
import amaralus.apps.rogue.services.screens.InventoryScreen;
import amaralus.apps.rogue.services.screens.MenuScreen;

public final class ServiceLocator {

    private static GameController gameController;
    private static LevelGenerator levelGenerator;
    private static GameScreen gameScreen;
    private static MenuScreen menuScreen;
    private static InventoryScreen inventoryScreen;

    private ServiceLocator() {}

    public static void register(GameController gameController) {
        ServiceLocator.gameController = gameController;
    }

    public static void register(LevelGenerator levelGenerator) {
        ServiceLocator.levelGenerator = levelGenerator;
    }

    public static void register(GameScreen gameScreen) {
        ServiceLocator.gameScreen = gameScreen;
    }

    public static void registerMenu(MenuScreen menuScreen) {
        ServiceLocator.menuScreen = menuScreen;
    }

    public static void registerInv(InventoryScreen inventoryScreen) {
        ServiceLocator.inventoryScreen = inventoryScreen;
    }

    public static GameController gameController() {
        return gameController;
    }

    public static LevelGenerator levelGenerator() {
        return levelGenerator;
    }

    public static GameScreen gameScreen() {
        return gameScreen;
    }

    public static MenuScreen menuScreen() {
        return menuScreen;
    }

    public static InventoryScreen inventoryScreen() {
        return inventoryScreen;
    }
}
