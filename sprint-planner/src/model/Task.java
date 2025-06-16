package model;

// Task.java
import utils.Constants;

import java.time.LocalDate;

public class Task {
    private final String id, title;
    private final Constants.TaskType type;
    private Constants.TaskStatus status;
    private final User assignee;
    private final LocalDate dueDate;

    public Task(String id, String title, Constants.TaskType type, User assignee, LocalDate dueDate) {
        if (id==null||title==null||type==null||assignee==null||dueDate==null)
            throw new IllegalArgumentException();
        this.id=id; this.title=title; this.type=type;
        this.status= Constants.TaskStatus.TODO; this.assignee=assignee; this.dueDate=dueDate;
    }

    public synchronized void setStatus(Constants.TaskStatus newStatus){
        if (status== Constants.TaskStatus.DONE)
            throw new IllegalStateException("Already DONE");
        if (status== Constants.TaskStatus.TODO && newStatus== Constants.TaskStatus.DONE)
            throw new IllegalStateException("Must go through IN_PROGRESS");
        this.status=newStatus;
    }

    public String getId(){return id;}
    public User getAssignee(){return assignee;}
    public Constants.TaskStatus getStatus(){return status;}
    public LocalDate getDueDate(){return dueDate;}
    @Override public String toString(){
        return String.format("Task[%s:%s→%s due %s]", id, status, assignee, dueDate);
    }
}
