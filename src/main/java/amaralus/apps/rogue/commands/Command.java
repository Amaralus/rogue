package amaralus.apps.rogue.commands;

import java.util.function.Consumer;

public class Command<E>{

    public static final Command<Object> NULLABLE_COM = new Command<>(null, null);

    private Runnable commandRunnable;
    private Consumer<E> commandConsumer;

    public Command(Runnable commandRunnable) {
        this.commandRunnable = commandRunnable;
    }

    public Command(Consumer<E> commandConsumer) {
        this.commandConsumer = commandConsumer;
    }

    public Command(Runnable commandRunnable, Consumer<E> commandConsumer) {
        this.commandRunnable = commandRunnable;
        this.commandConsumer = commandConsumer;
    }

    public void execute() {
        if (commandRunnable != null)
            commandRunnable.run();
    }

    public void execute(E entity) {
        if (commandConsumer != null)
            commandConsumer.accept(entity);
    }
}
