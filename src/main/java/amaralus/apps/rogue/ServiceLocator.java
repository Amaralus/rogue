package amaralus.apps.rogue;

import amaralus.apps.rogue.generators.LevelGenerator;
import amaralus.apps.rogue.graphics.GraphicsController;

public final class ServiceLocator {

    private static GameController gameController;
    private static GraphicsController graphicsController;
    private static LevelGenerator levelGenerator;

    private ServiceLocator() {}

    public static void provide(GameController gameController) {
        ServiceLocator.gameController = gameController;
    }

    public static void provide(GraphicsController graphicsController) {
        ServiceLocator.graphicsController = graphicsController;
    }

    public static void provide(LevelGenerator levelGenerator) {
        ServiceLocator.levelGenerator = levelGenerator;
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static GraphicsController getGraphicsController() {
        return graphicsController;
    }

    public static LevelGenerator getLevelGenerator() {
        return levelGenerator;
    }
}
