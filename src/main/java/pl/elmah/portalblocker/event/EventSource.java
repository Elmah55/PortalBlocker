package pl.elmah.portalblocker.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents event that listener can subscribe to. All subscribed listeners will be notified
 * when event is invoked
 *
 * @param <T> Type of parameter that will be provided to handler when event is invoked
 */
public class EventSource<T> {
    private List<EventHandler<T>> Listeners = new ArrayList<>();

    public void AddListener(EventHandler<T> handler) {
        Listeners.add(handler);
    }

    public void RemoveListener(EventHandler<T> handler) {
        Listeners.remove(handler);
    }

    public void Invoke(T eventArg) {
        for (EventHandler<T> handler : Listeners) {
            handler.Handle(eventArg);
        }
    }
}
