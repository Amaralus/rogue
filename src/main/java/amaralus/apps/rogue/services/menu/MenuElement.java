package amaralus.apps.rogue.services.menu;

import amaralus.apps.rogue.commands.Command;

import java.util.Objects;

public class MenuElement {

    private MenuElement next;
    private MenuElement previous;

    protected String text;
    protected final Command<Object> command;

    public MenuElement(String text, Runnable runnable) {
        this(text, new Command<>(runnable));
    }

    public MenuElement(String text, Command<Object> command) {
        this.text = text;
        this.command = command;
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

    public Command<Object> getCommand() {
        return command;
    }

    public void executeCommand() {
        command.execute();
    }

    public void executeCommandWithObject() {
        command.execute(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuElement that = (MenuElement) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, command);
    }
}
