package MyExeptions;

/**
 * Class representation for Invalid TokenException
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class InvalidTokenException extends Exception {

    /**
     * Class constructor.
     *
     * @param str error message
     */
    public InvalidTokenException(String str) {
        super(str);
    }

}
