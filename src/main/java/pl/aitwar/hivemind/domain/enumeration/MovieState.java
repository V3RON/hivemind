package pl.aitwar.hivemind.domain.enumeration;

import pl.aitwar.hivemind.exceptions.ContractViolationException;

/**
 * Implementation of movie state machine
 *
 * @author Szymon Chmal
 */
public enum MovieState {
    /**
     * A movie was added to database and waits for scheduling
     */
    WAITING,
    /**
     * A movie has been scheduled for next scraping
     */
    SCHEDULED,
    /**
     * A movie has been processed by min. one of scrapers successfully
     */
    PROCESSED,
    /**
     * All of scrapers failed to scrape a movie
     */
    FAILED;

    /**
     * Make sure that transition between states is legal
     *
     * @param state State to be applied
     * @return Same state as provied
     * @throws ContractViolationException when contract is broken
     */
    public MovieState makeTransition(MovieState state) {
        if (this.equals(state)) {
            throw new ContractViolationException("Cannot transit to the same state");
        }
        if (this.equals(WAITING) && !state.equals(SCHEDULED)) {
            throw new ContractViolationException(getErrorMessage(this, state));
        }

        if (this.equals(SCHEDULED) && state.equals(WAITING)) {
            throw new ContractViolationException(getErrorMessage(this, state));
        }

        if (this.equals(PROCESSED)) {
            throw new ContractViolationException(getErrorMessage(this, state));
        }

        if (this.equals(FAILED) && (state.equals(PROCESSED) || state.equals(SCHEDULED))) {
            throw new ContractViolationException(getErrorMessage(this, state));
        }

        return state;
    }

    private String getErrorMessage(MovieState from, MovieState to) {
        return "Cannot transit from " + from.toString() + " to " + to.toString();
    }
}
