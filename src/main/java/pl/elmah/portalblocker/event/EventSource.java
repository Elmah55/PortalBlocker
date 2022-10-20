package pl.elmah.portalblocker.event;

import java.util.ArrayList;
import java.util.List;

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
