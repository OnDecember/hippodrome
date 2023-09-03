import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @ParameterizedTest
    @CsvSource(value = {
            "null, 0, 0",
            "'', 0, 0",
            "name, -1, 0",
            "name, 0, -1"
    }, nullValues = "null")
    void throwsExceptionWhenCreateIllegalParametersHorse(String name, double speed, double distance) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
    }

    @Test
    void messageWhenCreateNullNameHorse() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " ", "\t", "\n"
    })
    void messageWhenCreateBlankNameHorse(String name) {
        try {
            new Horse(name, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -1, -10
    })
    void messageWhenCreateNegativeSpeedHorse(double speed) {
        try {
            new Horse("name", speed, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -1, -10
    })
    void messageWhenCreateNegativeDistanceHorse(double distance) {
        try {
            new Horse("name", 1, distance);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void getParameters() {
        String name = "name";
        double speed = 1.1;
        double distance = 2.3;

        Horse horse = new Horse(name, speed, distance);

        assertEquals(name, horse.getName());
        assertEquals(speed, horse.getSpeed());
        assertEquals(distance, horse.getDistance());
    }

    @Test
    void getZeroDistance() {
        String name = "name";
        double speed = 1.1;

        Horse horse = new Horse(name, speed);

        assertEquals(0, horse.getDistance());
    }

    //TODO: moveTest();
}