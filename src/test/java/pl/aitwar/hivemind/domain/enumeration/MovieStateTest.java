package pl.aitwar.hivemind.domain.enumeration;

import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.aitwar.hivemind.exceptions.ContractViolationException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.aitwar.hivemind.domain.enumeration.MovieState.*;

@SuppressWarnings("all")
public class MovieStateTest {
    @Test
    @MethodSource
    @ParameterizedTest
    @Tag("fast")
    public void shouldNotTransit(final MovieState from, final MovieState to) {
        assertThrows(ContractViolationException.class, () -> from.makeTransition(to));
    }

    @Test
    @MethodSource
    @ParameterizedTest
    @Tag("fast")
    public void shouldTransit(final MovieState from, final MovieState to) {
        from.makeTransition(to);
    }

    public static Stream<Arguments> shouldNotTransit() {
        return Stream.of(
                Arguments.of(WAITING, PROCESSED),
                Arguments.of(WAITING, FAILED),
                Arguments.of(WAITING, WAITING),
                Arguments.of(SCHEDULED, WAITING),
                Arguments.of(SCHEDULED, SCHEDULED),
                Arguments.of(PROCESSED, PROCESSED),
                Arguments.of(PROCESSED, SCHEDULED),
                Arguments.of(PROCESSED, WAITING),
                Arguments.of(PROCESSED, FAILED),
                Arguments.of(FAILED, PROCESSED),
                Arguments.of(FAILED, SCHEDULED),
                Arguments.of(FAILED, FAILED)
        );
    }

    public static Stream<Arguments> shouldTransit() {
        return Stream.of(
                Arguments.of(WAITING, SCHEDULED),
                Arguments.of(SCHEDULED, PROCESSED),
                Arguments.of(SCHEDULED, FAILED),
                Arguments.of(FAILED, WAITING)
        );
    }
}
