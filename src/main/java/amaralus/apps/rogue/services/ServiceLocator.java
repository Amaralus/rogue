package amaralus.apps.rogue.services;

import amaralus.apps.rogue.entities.items.ItemFactory;
import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.services.screens.GameScreen;
import amaralus.apps.rogue.services.screens.InventoryScreen;
import amaralus.apps.rogue.services.screens.GameMenuScreen;

public final class ServiceLocator {

    private static GameController gameController;
    private static LevelGenerator levelGenerator;
    private static GameScreen gameScreen;
    private static GameMenuScreen gameMenuScreen;
    private static InventoryScreen inventoryScreen;
    private static ItemFactory itemFactory;

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

    public static void register(GameMenuScreen menuScreen) {
        ServiceLocator.gameMenuScreen = menuScreen;
    }

    public static void register(InventoryScreen inventoryScreen) {
        ServiceLocator.inventoryScreen = inventoryScreen;
    }

    public static void register(ItemFactory itemFactory) {
        ServiceLocator.itemFactory = itemFactory;
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

    public static GameMenuScreen gameMenuScreen() {
        return gameMenuScreen;
    }

    public static InventoryScreen inventoryScreen() {
        return inventoryScreen;
    }

    public static ItemFactory itemFactory() {
        return itemFactory;
    }
}
