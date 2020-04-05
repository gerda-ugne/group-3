package hospital.staff;

import java.util.Date;

/**
 * Task Class defines a task that a professional can add to their
 * personal task list. The Task class is comparable.
 *
 * It contains the task name, description, the date when the
 * task is initialised and the date when it's due by.
 *
 * This feature is an optional extra.
 *
 */
public class Task implements Comparable<Task> {

    /**
     * Name of the task
     */
    String taskName;
    /**
     * Description of the task
     */
    String description;
    /**
     * Date when the task was initialised
     */
    Date initialised;
    /**
     * Date when the task is dueBy
     */
    Date dueBy;

    /**
     * Constructor for the Task class
     */
    public Task() {
        this("<undefined>", "<undefined>", null);
    }

    /**
     * Constructor for the Task class
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

    /**
     * Getter method for the task name
     * @return task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Setter method for the task name
     * @param taskName the task name to be set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Getter method for the description
     * @return description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for the description
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for the initialised date
     * @return date when the task was initialised
     */
    public Date getInitialised() {
        return initialised;
    }

    /**
     * Setter method for the initialised date
     * @param initialised initialised date to be set
     */
    public void setInitialised(Date initialised) {
        this.initialised = initialised;
    }

    /**
     * Getter method for the due by date.
     * @return date when the task is due by
     */
    public Date getDueBy() {
        return dueBy;
    }

    /**
     * Setter method for the due by date.
     * @param dueBy due by date to be set
     */
    public void setDueBy(Date dueBy) {
        this.dueBy = dueBy;
    }

    /**
     * Comparing method that compares
     * two different tasks according to their
     * due by date.
     * @param o task to be compared to
     * @return if o > task returns 1, if o < task returns -1, if elements are equal returns - 0.
     */
    @Override
    public int compareTo(Task o) {
        return getDueBy().compareTo(o.getDueBy());
    }

    /**
     * Prints data of the task.
     * If the task is past its due by date,
     * the user is warned.
     */
    public void printData()
    {
        System.out.println("\nTask: " + taskName);
        System.out.println("Initialised: " + initialised);
        System.out.println(description);
        System.out.println("Task is due by: " + dueBy);

        Date today = new Date();
        int comparator = (getDueBy().compareTo(today));
        if(comparator > 0) System.out.println("\nTHE DUE BY DATE HAS PASSED. PLEASE REMOVE THE TASK.");
    }

}
