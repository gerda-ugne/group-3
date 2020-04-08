package hospital.staff;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Task implements Comparable<Task>, Serializable {

    /**
     * Name of the task
     */
    private String taskName;
    /**
     * Description of the task
     */
    private String description;
    /**
     * Date when the task was initialised
     */
    private LocalDate initialised;
    /**
     * Date when the task is dueBy
     */
    private LocalDate dueBy;

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
    public Task(String taskName, String description, LocalDate dueBy) {
        this.taskName = taskName;
        this.description = description;
        this.dueBy = dueBy;
        this.initialised = LocalDate.now();
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
    public LocalDate getInitialised() {
        return initialised;
    }

    /**
     * Setter method for the initialised date
     * @param initialised initialised date to be set
     */
    public void setInitialised(LocalDate initialised) {
        this.initialised = initialised;
    }

    /**
     * Getter method for the due by date.
     * @return date when the task is due by
     */
    public LocalDate getDueBy() {
        return dueBy;
    }

    /**
     * Setter method for the due by date.
     * @param dueBy due by date to be set
     */
    public void setDueBy(LocalDate dueBy) {
        this.dueBy = dueBy;
    }

    /**
     * Comparing method that compares
     * two different tasks according to their
     * due by date.
     * @param o task to be compared to
     * @return if bigger than 0 task returns 1, if smaller task returns -1, if elements are equal returns - 0.
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

        LocalDate today = LocalDate.now();
        int comparator = (getDueBy().compareTo(today));
        if(comparator < 0) System.out.println("\nTHE DUE BY DATE HAS PASSED. PLEASE REMOVE THE TASK.");
    }

}
