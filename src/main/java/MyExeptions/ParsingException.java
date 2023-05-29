package MyExeptions;

/**
 * Class representation for Parsing Exception
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class ParsingException extends Exception {

    /**
     * Class constructor.
     *
     * @param str error message
     */
    public ParsingException(String str) {
        super(str);
    }
}
