package amaralus.apps.rogue.services.menu;

import amaralus.apps.rogue.services.Action;

import java.util.Objects;

public class MenuElement {

    private MenuElement next;
    private MenuElement previous;

    private final String text;
    private final Action action;

    public MenuElement(String text, Action action) {
        this.text = text;
        this.action = action;
    }

    public MenuElement getNext() {
        return next;
    }

    public void setNext(MenuElement next) {
        this.next = next;
    }

    public MenuElement getPrevious() {
        return previous;
    }

    public void setPrevious(MenuElement previous) {
        this.previous = previous;
    }

    public String getText() {
        return text;
    }

    public Action getAction() {
        return action;
    }

    public void performAction() {
        action.perform();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuElement that = (MenuElement) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, action);
    }
}
