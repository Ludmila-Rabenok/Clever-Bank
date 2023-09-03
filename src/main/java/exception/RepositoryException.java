package exception;

/**
 * This class is designed to throw exceptions in case of errors in connecting to the database and its incorrect operation.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Throwable course) {
        super(course);
    }

}
