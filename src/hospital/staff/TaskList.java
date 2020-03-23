package hospital.staff;

import java.util.*;

/**
 * The TaskList class contains the list of
 * the tasks that professionals can add to their
 * each own personal task list.
 *
 * This class contains methods to manipulate the tasks.
 *
 * This feature is an optional extra.
 */
public class TaskList {

    List<Task> taskList;

    /**
     * Default constructor for TaskList class
     */
    public TaskList()
    {
        taskList = new ArrayList<Task>();
    }

    /**
     * Adds a task to the personal task list.
     * @param newTask - task to be added
     * @return true/false whether the addition was successful
     */
    public boolean addTask(Task newTask)
    {
       if(taskList.contains(newTask))
       {
           System.out.println("This task already exists!");
           return false;
       }
        else taskList.add(newTask);
        return true;
    }

    /**
     * Deletes a task from the personal task list.
     * @param toDelete - task to delete
     * @return deleted task
     */
    public Task deleteTask(Task toDelete)
    {
        try {

            Task temp = toDelete;
            taskList.remove(toDelete);

            return toDelete;
        } catch (NullPointerException e) {

            System.out.println("Task not found - nothing to remove.");
            return null;
        }
    }


    /**
     * Sorts tasks by initialise date.
     * @return sorted taskList
     */
    public List<Task> sortTasksByInitialisedDate()
    {
        List<Task> copy = new ArrayList<>(List.copyOf(taskList));
        Collections.sort(copy);
        return copy;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
