package hospital.timeLogger;

/**
 * TimeLogger class logs the time methods take
 * to complete.
 *
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
     * Constructor of the TimeLogger class
     * @param startTime time when the method started
     */
    public TimeLogger(long startTime)
    {
        endTime = System.nanoTime();
        calculateElapsedTime();
    }

    /**
     * The method calculates the elapsed time
     * and displays the result to the user.
     */
    public void calculateElapsedTime()
    {
        long totalTime = endTime- startTime/1000;
        System.out.println("The method took " + totalTime + " milliseconds.");
    }
}
