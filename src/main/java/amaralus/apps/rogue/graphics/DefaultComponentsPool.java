package amaralus.apps.rogue.graphics;

import static amaralus.apps.rogue.graphics.EntitySymbol.*;
import static amaralus.apps.rogue.graphics.Palette.*;

public final class DefaultComponentsPool {

    public static final GraphicsComponent EMPTY = new GraphicsComponent(SPACE, BLACK_BLUE);
    public static final GraphicsComponent FLOOR_ROOM = new GraphicsComponent(ROOM_FLOOR, GRAY);

    public static final GraphicsComponent VERTICAL_WALL = new GraphicsComponent(WALL_VERTICAL, WHITE_GRAY);
    public static final GraphicsComponent HORIZONTAL_WALL = new GraphicsComponent(WALL_HORIZONTAL, WHITE_GRAY);

    public static final GraphicsComponent TR_CORNER = new GraphicsComponent(WALL_TOP_RIGHT, WHITE_GRAY);
    public static final GraphicsComponent TL_CORNER = new GraphicsComponent(WALL_TOP_LEFT, WHITE_GRAY);
    public static final GraphicsComponent BR_CORNER = new GraphicsComponent(WALL_BOTTOM_RIGHT, WHITE_GRAY);
    public static final GraphicsComponent BL_CORNER = new GraphicsComponent(WALL_BOTTOM_LEFT, WHITE_GRAY);

    private DefaultComponentsPool() {}
}
