package amaralus.apps.rogue.services.game;

import java.util.ArrayList;
import java.util.List;

public class EventJournal {

    private List<String> eventLogs = new ArrayList<>();

    public void clear() {
        eventLogs = new ArrayList<>();
    }

    public void logEvent(String message) {
        eventLogs.add(message);
    }

    public void logEvent(String message, Object... args) {
        logEvent(String.format(message, args));
    }

    public String getLastEvent() {
        return eventLogs.isEmpty() ? "" : eventLogs.get(eventLogs.size() - 1);
    }

    public List<String> getLastEvents(int count) {
        return count > eventLogs.size() ?
                eventLogs :
                eventLogs.subList(eventLogs.size() - count, eventLogs.size());
    }
}
