import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    void throwsIllegalArgumentExceptionWhenCreateNullParameterHippodrome(List<Horse> horses) {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    void messageWhenCreateNullParameterHippodrome() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    void messageWhenCreateEmptyParameterHippodrome() {
        try {
            new Hippodrome(new ArrayList<>(0));
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    void getHorses() {
        AtomicInteger ai  = new AtomicInteger(0);
        List<Horse> horses = Stream.generate(() -> new Horse("name" + ai.getAndIncrement(), 1, 1))
                .limit(30)
                .collect(Collectors.toList());
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveTest() {
        List<Horse> list = Stream.generate(() -> Mockito.mock(Horse.class))
                .limit(50)
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();

        hippodrome.getHorses()
                .forEach((horse -> Mockito.verify(horse).move()));
    }

    @Test
    void getWinnerTest() {

        Horse h1 = new Horse("name1", 1, 1);
        Horse h2 = new Horse("name2", 3, 3);
        Horse h3 = new Horse("name3", 2, 2);
        List<Horse> horses = new ArrayList<>() {{
            add(h1);
            add(h2);
            add(h3);
        }};

        Hippodrome hippodrome = new Hippodrome(horses);

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class))
        {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            hippodrome.move();
        }

        assertEquals(h2, hippodrome.getWinner());

    }
}