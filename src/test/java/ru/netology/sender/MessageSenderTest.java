package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class MessageSenderTest {

    @ParameterizedTest
    @MethodSource("methodSource")
    public void test_param_method_source(String IP, String actual) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.149"))
                .thenReturn(new Location("null", Country.USA, "null", 0));
        Mockito.when(geoService.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, IP);

        String expected = messageSender.send(headers);
        Assertions.assertEquals(actual, expected);
    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of("96.44.183.149", "Welcome"),
                Arguments.of("172.0.32.11", "Добро пожаловать")
        );
    }

    @Test
    public void testLocation_byIP() {
        String IP = GeoServiceImpl.MOSCOW_IP;
        Location expectedResult = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        GeoServiceImpl Actual = new GeoServiceImpl();
        Assertions.assertEquals(Actual.byIp(IP).getCity(), expectedResult.getCity());
        Assertions.assertEquals(Actual.byIp(IP).getCountry(), expectedResult.getCountry());
        Assertions.assertEquals(Actual.byIp(IP).getStreet(), expectedResult.getStreet());
        Assertions.assertEquals(Actual.byIp(IP).getBuiling(), expectedResult.getBuiling());
    }

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
