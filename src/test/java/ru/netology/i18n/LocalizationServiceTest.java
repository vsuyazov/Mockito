package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("locale")
    public void test_locale(Country country, String message) {
        LocalizationService result = new LocalizationServiceImpl();
        Assertions.assertEquals(result.locale(country), message);
    }

    public static Stream<Arguments> locale() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome")
        );
    }
}
