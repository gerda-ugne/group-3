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
     * @return sorted taskList
     */
    public List<Task> sortTasksByDueByDate()
    {
        List<Task> copy = new ArrayList<>((taskList));
        Collections.sort(copy);
        return copy;
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
        for (Task task:taskList
             ) {
            task.printData();
            System.out.println();
        }
    }

}
