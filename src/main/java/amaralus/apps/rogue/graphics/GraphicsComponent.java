package amaralus.apps.rogue.graphics;

import javafx.scene.paint.Color;

public class GraphicsComponent {

    private EntitySymbol entitySymbol;
    private Color color;

    public GraphicsComponent(EntitySymbol entitySymbol, Color color) {
        this.entitySymbol = entitySymbol;
        this.color = color;
    }

    public EntitySymbol getEntitySymbol() {
        return entitySymbol;
    }

    public Color getColor() {
        return color;
    }

    public void setEntitySymbol(EntitySymbol entitySymbol) {
        this.entitySymbol = entitySymbol;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
