package hospital.staff;

import java.util.Date;

/**
 * Task Class defines a task that a professional can add to their
 * personal task list.
 *
 * It contains the task name, description, the date when the
 * task is initialised and the date when it's due by.
 *
 * This feature is an optional extra.
 *
 */
public class Task implements Comparable<Task> {

    String taskName;
    String description;
    Date initialised;
    Date dueBy;

    /**
     * Default constructor for Task class
     */
    public Task() {
        this("<undefined>", "<undefined>", null);

    }

    /**
     * Default constructor for Task class
     *
     * @param taskName    name of the task
     * @param description short description of the task
     * @param dueBy       date when the task is due
     */
    public Task(String taskName, String description, Date dueBy) {
        this.taskName = taskName;
        this.description = description;
        this.dueBy = dueBy;
        this.initialised = new Date();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInitialised() {
        return initialised;
    }

    public void setInitialised(Date initialised) {
        this.initialised = initialised;
    }

    public Date getDueBy() {
        return dueBy;
    }

    public void setDueBy(Date dueBy) {
        this.dueBy = dueBy;
    }

    @Override
    public int compareTo(Task o) {
        return getInitialised().compareTo(o.getInitialised());
    }

    /**
     * Prints data of the task
     */
    public void printData()
    {
        System.out.println("Task: " + taskName);
        System.out.println("Initalised: " + initialised);
        System.out.println(description);
        System.out.println("Task is due by: " + dueBy);
    }

}
