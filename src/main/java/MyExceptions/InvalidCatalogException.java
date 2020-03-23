package MyExceptions;

public class InvalidCatalogException extends Exception {
    /**
     * Exceptie creata de mine pentru un catalog invalid
     */
    public InvalidCatalogException() {
        super("Invalid catalog file.");
    }
}
