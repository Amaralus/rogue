package amaralus.apps.rogue.services;

import amaralus.apps.rogue.generators.LevelGenerator;

public final class ServiceLocator {

    private static GameController gameController;
    private static LevelGenerator levelGenerator;

    private ServiceLocator() {}

    public static void provide(GameController gameController) {
        ServiceLocator.gameController = gameController;
    }

    public static void provide(LevelGenerator levelGenerator) {
        ServiceLocator.levelGenerator = levelGenerator;
    }

    public static GameController gameController() {
        return gameController;
    }

    public static LevelGenerator levelGenerator() {
        return levelGenerator;
    }
}
