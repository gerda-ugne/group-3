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

    /**
     * List that contains all professional's tasks
     */
    List<Task> taskList;

    /**
     * Constructor for the TaskList class
     */
    public TaskList()
    {
        taskList = new ArrayList<Task>();
    }

    /**
     * Adds a task to the personal task list.
     * The list is sorted by initialised date afterwards.
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
        sortTasksByDueByDate();
        return true;
    }

    /**
     * Deletes a task from the personal task list.
     * @param toDelete - task to delete
     * @return True if the deletion was successful.
     */
    public boolean deleteTask(Task toDelete)
    {
        return taskList.remove(toDelete);
    }


    /**
     * Sorts tasks by due by date.
     */
    public void sortTasksByDueByDate()
    {
        Collections.sort(taskList);
    }

    /**
     * Getter method for the task list.
     * @return the task list
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Setter method for the task list.
     * @param taskList task list to be set
     */
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Displays the task list.
     */
    public void displayTaskList()
    {
        if(taskList.isEmpty()) System.out.println("The task list is currently empty.");
        for (Task task:taskList
             ) {
            task.printData();
            System.out.println();
        }
    }

    /**
     * Finds a task in the task list
     * @param taskName task to find
     * @return task if found, null if not
     */
    public Task findTask(String taskName)
    {
        for (Task task : taskList) {
            if (task.getTaskName().equals(taskName)) {

                return task;
            }
        }

        return null;
    }

}
