package amaralus.apps.rogue.graphics;

public enum EntitySymbol {
    SMILING_FACE('\u263a'),
    HEART('\u2665'),
    WALL_HORIZONTAL('\u2550'),
    WALL_VERTICAL('\u2551'),
    WALL_VERTICAL_HORIZONTAL('\u256c'),
    WALL_BOTTOM_RIGHT('\u2554'),
    WALL_BOTTOM_LEFT('\u2557'),
    WALL_TOP_RIGHT('\u255a'),
    WALL_TOP_LEFT('\u255d'),
    CENTRAL_DOT('\u2219'),
    LINE_VERTICAL('\u2502'),
    LINE_HORIZONTAL('\u2500'),
    LINE_VERTICAL_HORIZONTAL('\u253c'),
    SPACE(' ');

    private char symbol;

    EntitySymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getChar() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
