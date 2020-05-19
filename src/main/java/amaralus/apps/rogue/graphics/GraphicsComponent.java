package amaralus.apps.rogue.graphics;

import javafx.scene.paint.Color;

public class GraphicsComponent {

    private EntitySymbol entitySymbol;
    private Color color;
    private Color warFogColor;

    public GraphicsComponent(EntitySymbol entitySymbol, Color color) {
        this.entitySymbol = entitySymbol;
        this.color = color;
    }

    public GraphicsComponent(EntitySymbol entitySymbol, Color color, Color warFogColor) {
        this.entitySymbol = entitySymbol;
        this.color = color;
        this.warFogColor = warFogColor;
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

    public Color getWarFogColor() {
        return warFogColor;
    }

    public void setWarFogColor(Color warFogColor) {
        this.warFogColor = warFogColor;
    }
}
