package bookstore.exception;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException(String message) {
        super(message);
    }
}
