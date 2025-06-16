package controller;

// SprintController.java
import model.Sprint;
import model.Task;
import model.User;
import service.SprintPlannerService;
import utils.Constants;
import utils.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class SprintController {
    private final SprintPlannerService svc;
    public SprintController(SprintPlannerService svc){
        this.svc = svc;
    }

    public String createSprint(LocalDate start, LocalDate end){
        Sprint s = svc.createSprint(IdGenerator.nextId("s"), start, end);
        return s.getId();
    }

    public String createAndAddTask(String sprintId, Constants.TaskType type, String title, User user, LocalDate due) {
        Task t = svc.createTask(title, type, user, due);
        svc.addTaskToSprint(sprintId, t);
        return t.getId();
    }

    public void updateTaskStatus(String sprintId, String taskId, Constants.TaskStatus status){
        svc.changeTaskStatus(sprintId, taskId, status);
    }

    public List<Task> listUserTasks(String sprintId, User u){
        return svc.getUserTasks(sprintId, u);
    }

    public List<Task> listDelayed(String sprintId){
        return svc.getDelayed(sprintId);
    }
}
