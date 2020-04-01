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
     * start time is recorded
     */
    public TimeLogger()
    {
        startTime = System.nanoTime();
    }

    /**
     * The method calculates the elapsed time
     * and displays the result to the user.
     */
    public void calculateElapsedTime()
    {
        endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("The method took " + totalTime + " milliseconds.");
    }
}
