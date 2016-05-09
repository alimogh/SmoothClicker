package pylapp.smoothclicker.android.tools;

/**
 * Exception to use if the JSON file to process to get the points to click on is missing
 *
 * @version 1.0.0
 * @since 04/05/2016
 * @see Exception
 */
public class MissingJsonPointsFileException extends Exception {

    /**
     *
     * @param message - The message to display, about the error
     */
    public MissingJsonPointsFileException( String message ){
        super(message);
    }

}
