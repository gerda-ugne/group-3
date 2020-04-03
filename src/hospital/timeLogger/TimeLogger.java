package hospital.timeLogger;

/**
 * TimeLogger class logs the time methods take
 * to complete.
 *
 * In order to capture the time, the object of this
 * class has to be initiated at the start of the
 * method and then the calculateElapsedTime()
 * method has to be called at the end of the method.
 *
 */
public class TimeLogger {

    /**
     * Name of the task to log time of
     */
    String taskName;
    /**
     * Start of the time logging
     */
    long startTime;

    /**
     * End of the time logging
     */
    long endTime;

    /**
     * Constructor of the TimeLogger class.
     * When object of the class is initiated,
     * start time is recorded.
     * and a task name is set.
     */
    public TimeLogger(String taskName)
    {
        startTime = System.nanoTime();
        this.taskName = taskName;
    }

    /**
     * The method calculates the elapsed time
     * and displays the result to the user.
     */
    public void calculateElapsedTime()
    {
        endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("The " + taskName + " task took " + totalTime + " milliseconds.");
    }
}
