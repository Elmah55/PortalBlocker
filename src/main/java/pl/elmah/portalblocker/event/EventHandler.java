package pl.elmah.portalblocker.event;

/**
 * Provides functionality for handling event
 *
 * @param <T> Type of arg that is provided when event that this
 *            handler is listening to is invoked
 */
public interface EventHandler<T> {
    void Handle(T eventArg);
}
