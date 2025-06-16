package service;

import model.Sprint;
import model.Task;
import model.User;
import utils.Constants;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SprintManager {
    private static final int MAX_TASKS = 20;
    private static final int MAX_IN_PROGRESS = 2;

    public void addTask(Sprint sprint, Task task) {
        if (sprint.getTasks().size() >= MAX_TASKS)
            throw new IllegalStateException("Sprint is full.");

        long inProgressCount = sprint.getTasks().values().stream()
                .filter(t -> t.getAssignee().equals(task.getAssignee()))
                .filter(t -> t.getStatus() == Constants.TaskStatus.IN_PROGRESS)
                .count();

        if (task.getStatus() == Constants.TaskStatus.IN_PROGRESS && inProgressCount >= MAX_IN_PROGRESS)
            throw new IllegalStateException("User already has 2 tasks in progress.");

        sprint.addRawTask(task);
    }

    public void changeStatus(Sprint sprint, String taskId, Constants.TaskStatus newStatus) {
        Task task = sprint.getTask(taskId);
        if (task == null) throw new NoSuchElementException("Task not found.");

        if (newStatus == Constants.TaskStatus.IN_PROGRESS) {
            long inProgressCount = sprint.getTasks().values().stream()
                    .filter(t -> t.getAssignee().equals(task.getAssignee()))
                    .filter(t -> t.getStatus() == Constants.TaskStatus.IN_PROGRESS)
                    .count();

            if (inProgressCount >= MAX_IN_PROGRESS)
                throw new IllegalStateException("User has max in-progress tasks.");
        }

        task.setStatus(newStatus);
    }

    public List<Task> getDelayedTasks(Sprint sprint) {
        LocalDate today = LocalDate.now();
        return sprint.getTasks().values().stream()
                .filter(t -> t.getStatus() != Constants.TaskStatus.DONE)
                .filter(t -> t.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByUser(Sprint sprint, User user) {
        return sprint.getTasks().values().stream()
                .filter(t -> t.getAssignee().equals(user))
                .collect(Collectors.toList());
    }
}
