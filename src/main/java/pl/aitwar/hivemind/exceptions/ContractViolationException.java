package pl.aitwar.hivemind.exceptions;

/**
 * Exception being thrown when given contract would be broken
 * in effect of operation
 *
 * @author Szymon Chmal
 */
public class ContractViolationException extends RuntimeException {
    public ContractViolationException(String message) {
        super(message);
    }
}
