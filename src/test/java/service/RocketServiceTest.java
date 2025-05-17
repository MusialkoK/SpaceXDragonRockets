package service;

import com.kmu.dataobject.Rocket;
import com.kmu.service.impl.RocketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RocketServiceTest {
    @InjectMocks
    private RocketService rocketService;

    @Mock
    private HashMap<String, Rocket> rocketMap = new HashMap<>();

    @Test
    void addRocketWithNotNullUniqueNameToMap() {
        //given
        String rocketName = "Dragon 1";
        Rocket rocket = new Rocket(rocketName);
        when(rocketMap.put(rocketName, rocket)).thenCallRealMethod();
        when(rocketMap.containsKey(rocketName)).thenCallRealMethod();
        when(rocketMap.size()).thenCallRealMethod();

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertTrue(isAdded);
        verify(rocketMap, times(1)).put(rocketName, rocket);
        assertTrue(rocketMap.containsKey(rocketName));
        assertEquals(1, rocketMap.size());
    }

    @Test
    void doNotAddRocketWithNullName() {
        //given
        String rocketName = null;
        Rocket rocket = new Rocket(rocketName);
        when(rocketMap.size()).thenCallRealMethod();

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertFalse(isAdded);
        verify(rocketMap, never()).put(any(), any());
        assertEquals(0, rocketMap.size());
    }
}
