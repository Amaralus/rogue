package amaralus.apps.rogue.graphics;

import static amaralus.apps.rogue.graphics.EntitySymbol.*;
import static amaralus.apps.rogue.graphics.Palette.*;

public final class GraphicsComponentsPool {

    public static final GraphicsComponent EMPTY_CELL = new GraphicsComponent(SPACE, BLACK_BLUE);
    public static final GraphicsComponent ROOM_FLOOR = new GraphicsComponent(COLON, GRAY, DARK_GRAY);
    public static final GraphicsComponent CORRIDOR_FLOR = new GraphicsComponent(SHADE, GRAY, DARK_GRAY);

    public static final GraphicsComponent VERTICAL_WALL = new GraphicsComponent(WALL_VERTICAL, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent HORIZONTAL_WALL = new GraphicsComponent(WALL_HORIZONTAL, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent DOOR = new GraphicsComponent(WALL_VERTICAL_HORIZONTAL, WHITE_GRAY, DARK_GRAY);

    public static final GraphicsComponent TR_CORNER = new GraphicsComponent(WALL_TOP_RIGHT, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent TL_CORNER = new GraphicsComponent(WALL_TOP_LEFT, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent BR_CORNER = new GraphicsComponent(WALL_BOTTOM_RIGHT, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent BL_CORNER = new GraphicsComponent(WALL_BOTTOM_LEFT, WHITE_GRAY, DARK_GRAY);

    public static final GraphicsComponent STAIRS = new GraphicsComponent(IDENTICAL_TO, WHITE_GRAY, DARK_GRAY);
    public static final GraphicsComponent TRAP = new GraphicsComponent(DIAMOND_SUIT, WHITE_GRAY, DARK_GRAY);

    public static final GraphicsComponent PLAYER = new GraphicsComponent(SMILING_FACE, YELLOW);
    public static final GraphicsComponent ZOMBIE = new GraphicsComponent(Z, WHITE_GREEN);

    public static final GraphicsComponent GOLD = new GraphicsComponent(ASTERISK, YELLOW, BLACK_BLUE);
    public static final GraphicsComponent AMULET_OF_YENDOR = new GraphicsComponent(YEN, PURPLE, BLACK_BLUE);

    private GraphicsComponentsPool() {}
}
