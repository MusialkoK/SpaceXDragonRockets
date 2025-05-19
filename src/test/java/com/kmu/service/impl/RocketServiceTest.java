package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RocketServiceTest {

    private RocketService rocketService;
    @Spy
    private Set<Rocket> rocketSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        rocketService = new RocketService(rocketSet);
    }

    @Test
    void addRocketWithNotNullUniqueNameToMap() {
        //given
        String rocketName = "Dragon 1";
        Rocket rocket = new Rocket(rocketName);

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertTrue(isAdded);
        verify(rocketSet, times(1)).add(rocket);
        assertTrue(rocketSet.contains(rocket));
        assertEquals(1, rocketSet.size());
    }

    @Test
    void doNotAddRocketWithNullName() {
        //given
        String rocketName = null;
        Rocket rocket = new Rocket(rocketName);

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertFalse(isAdded);
        verify(rocketSet, never()).add(any());
        assertTrue(rocketSet.isEmpty());
    }

    @Test
    void doNotAddRocketWithEmptyName() {
        //given
        String rocketName = "";
        Rocket rocket = new Rocket(rocketName);

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertFalse(isAdded);
        verify(rocketSet, never()).add(any());
        assertTrue(rocketSet.isEmpty());
    }

    @Test
    void doNotAddRocketIfNameNotUnique() {
        //given
        String rocketName = "Dragon 1";
        Rocket firstRocket = new Rocket(rocketName);
        rocketService.addNewRocket(firstRocket);
        Rocket secondRocket = new Rocket(rocketName);

        //when
        boolean isAdded = rocketService.addNewRocket(secondRocket);
        //then
        assertFalse(isAdded);
        verify(rocketSet, times(1)).add(any());
        assertTrue(rocketSet.contains(firstRocket));
        assertEquals(1, rocketSet.size());
    }

    @Test
    void doNotAddIfRocketNull() {
        //given
        Rocket rocket = null;

        //when
        boolean isAdded = rocketService.addNewRocket(rocket);
        //then
        assertFalse(isAdded);
        verify(rocketSet, never()).add(any());
    }

    @Test
    void isSingleton() {
        //given + when
        RocketService rocketService1 = RocketService.getInstance();
        RocketService rocketService2 = RocketService.getInstance();

        //then

        assertNotNull(rocketService1);
        assertEquals(rocketService1, rocketService2);
    }

    @Test
    void dontChangeRocketStatusToInSpaceIfInRepair() {
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        rocket.setStatus(RocketStatus.IN_REPAIR);

        //when
        boolean isAssigned = rocketService.assignRocketToMission(rocket, mission);

        //then
        assertTrue(isAssigned);
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
        assertEquals(mission, rocket.getCurrentMission());
    }

    @Test
    void clearRocketCurrentMission(){
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        rocket.setCurrentMission(mission);

        //when
        rocketService.clearRocketCurrentMission(rocket);

        //then
        assertNull(rocket.getCurrentMission());
    }

    @Test
    void clearRocketCurrentMissionRocketNull(){
        //given + when
        Rocket rocket = null;

        //then
        assertDoesNotThrow(() -> rocketService.clearRocketCurrentMission(rocket));
    }
}
