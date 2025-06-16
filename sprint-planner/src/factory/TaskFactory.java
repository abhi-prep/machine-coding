package factory;

import model.Task;
import model.User;
import utils.Constants;
import utils.IdGenerator;

import java.time.LocalDate;
import java.util.UUID;

public class TaskFactory {
    public static Task create(Constants.TaskType type, String title, User a, LocalDate due){
        return new Task(IdGenerator.nextId("t"), title, type, a, due);
    }
}
