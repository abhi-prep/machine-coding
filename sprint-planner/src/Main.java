import controller.SprintController;
import model.Task;
import model.User;
import repository.SprintRepository;
import service.SprintManager;
import service.SprintPlannerService;
import utils.Constants;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SprintRepository repo = new SprintRepository();
        SprintManager sprintManager = new SprintManager();
        SprintPlannerService service = new SprintPlannerService(repo, sprintManager);

        SprintController ctrl = new SprintController(service);

        User alice = new User("u1","Alice");
        User bob   = new User("u2","Bob");

        // 1. Create Sprint
        String sprintId = ctrl.createSprint(LocalDate.now(), LocalDate.now().plusDays(14));
        System.out.println("Sprint ID: " + sprintId);

        // 2. Create & add tasks
        String t1 = ctrl.createAndAddTask(sprintId, Constants.TaskType.STORY, "Login UI", alice, LocalDate.now().plusDays(3));
        String t2 = ctrl.createAndAddTask(sprintId, Constants.TaskType.BUG,   "Crash on save", alice, LocalDate.now().minusDays(1));
        String t3 = ctrl.createAndAddTask(sprintId, Constants.TaskType.FEATURE, "Export CSV", bob, LocalDate.now().plusDays(5));

        // 3. Update statuses
        ctrl.updateTaskStatus(sprintId, t1, Constants.TaskStatus.IN_PROGRESS);
        ctrl.updateTaskStatus(sprintId, t1, Constants.TaskStatus.DONE);

        // 4. List tasks for Alice
        List<Task> aliceTasks = ctrl.listUserTasks(sprintId, alice);
        System.out.println("Alice’s tasks: " + aliceTasks);

        // 5. List delayed
        List<Task> delayed = ctrl.listDelayed(sprintId);
        System.out.println("Delayed tasks: " + delayed);
    }
}
