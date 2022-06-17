package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceTest {

    @Test
    public void testLocation_byIP() {
        String IP = GeoServiceImpl.MOSCOW_IP;
        Location expectedResult = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        GeoServiceImpl service = new GeoServiceImpl();
        Location Actual = service.byIp(IP);
        Assertions.assertEquals(Actual.getCity(), expectedResult.getCity());
        Assertions.assertEquals(Actual.getCountry(), expectedResult.getCountry());
        Assertions.assertEquals(Actual.getStreet(), expectedResult.getStreet());
        Assertions.assertEquals(Actual.getBuiling(), expectedResult.getBuiling());
    }
}
