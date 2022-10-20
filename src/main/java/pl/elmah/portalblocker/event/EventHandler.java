package pl.elmah.portalblocker.event;

public interface EventHandler<T> {
    void Handle(T eventArg);
}
