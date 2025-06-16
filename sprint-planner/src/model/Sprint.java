package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sprint {
    private final String id;
    private final LocalDate startDate, endDate;
    private final Map<String, Task> tasks = new ConcurrentHashMap<>();

    public Sprint(String id, LocalDate start, LocalDate end) {
        this.id = id; this.startDate = start; this.endDate = end;
    }

    public Map<String, Task> getTasks() {
        return Collections.unmodifiableMap(tasks);
    }

    public void addRawTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeRawTask(String taskId) {
        tasks.remove(taskId);
    }

    public Task getTask(String id) {
        return tasks.get(id);
    }

    public String getId() {
        return id;
    }
}
