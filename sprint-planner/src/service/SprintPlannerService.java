// SprintPlannerService.java
package service;

import factory.TaskFactory;
import model.Sprint;
import model.Task;
import model.User;
import repository.SprintRepository;
import utils.Constants;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class SprintPlannerService {
    private final SprintRepository repo;
    private final SprintManager manager;
    public SprintPlannerService(SprintRepository repo, SprintManager manager) {
        this.repo = repo;
        this.manager = manager;
    }

    public Sprint createSprint(String id, LocalDate start, LocalDate end) {
        if (repo.exists(id)) {
            throw new IllegalArgumentException("Sprint ID already exists.");
        }
        return repo.save(new Sprint(id, start, end));
    }

    public Task createTask( String title, Constants.TaskType type, User user, LocalDate dueDate) {
        return TaskFactory.create(type, title, user, dueDate);
    }

    public void addTaskToSprint(String sprintId, Task task) {
        Sprint s = repo.findById(sprintId)
                .orElseThrow(() -> new NoSuchElementException("Sprint not found"));
        manager.addTask(s, task);
    }

    public void changeTaskStatus(String sprintId, String taskId, Constants.TaskStatus status) {
        Sprint s = repo.findById(sprintId)
                .orElseThrow(() -> new NoSuchElementException("Sprint not found"));
        manager.changeStatus(s, taskId, status);
    }

    public List<Task> getDelayed(String sprintId) {
        return manager.getDelayedTasks(
                repo.findById(sprintId).orElseThrow()
        );
    }

    public List<Task> getUserTasks(String sprintId, User u) {
        return manager.getTasksByUser(
                repo.findById(sprintId).orElseThrow(), u
        );
    }
}
