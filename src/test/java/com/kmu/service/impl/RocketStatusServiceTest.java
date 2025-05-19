package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RocketStatusServiceTest {

    private RocketStatusService rocketStatusService;
    @Spy
    private MissionStatusService missionStatusService;

    @BeforeEach
    void setUp() {
        rocketStatusService = new RocketStatusService(missionStatusService);
    }

    @Test
    void isStatusChangedToInSpace() {
        //given
        Rocket rocket = new Rocket("Dragon 1");

        //when
        boolean inSpace = rocketStatusService.changeStatusToInSpace(rocket);

        //then
        assertTrue(inSpace);
        assertEquals(RocketStatus.IN_SPACE, rocket.getStatus());
    }

    @Test
    void ifRocketIsNullWhenChangeToInSpaceReturnFalse() {
        //given
        Rocket rocket = null;

        //when
        boolean inSpace = rocketStatusService.changeStatusToInSpace(rocket);

        //then
        assertFalse(inSpace);
    }

    @Test
    void isSingleton() {
        //given + when
        RocketStatusService rocketStatusService1 = RocketStatusService.getInstance();
        RocketStatusService rocketStatusService2 = RocketStatusService.getInstance();

        //then

        assertNotNull(rocketStatusService1);
        assertEquals(rocketStatusService1, rocketStatusService2);
    }

    @Test
    void isStatusChangedToInRepairWithCurrentMission() {
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Mission mission = new Mission("Luna");
        rocket.setCurrentMission(mission);

        //when
        boolean inRepair = rocketStatusService.changeStatusToInRepair(rocket);

        //then
        assertTrue(inRepair);
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
        assertEquals(MissionStatus.PENDING, rocket.getCurrentMission().getStatus());
    }

    @Test
    void isStatusChangedToInRepairWithoutCurrentMission() {
        //given
        Rocket rocket = new Rocket("Dragon 1");

        //when
        boolean inRepair = rocketStatusService.changeStatusToInRepair(rocket);

        //then
        assertTrue(inRepair);
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
    }

    @Test
    void ifRocketIsNullWhenChangeToInRepairReturnFalse() {
        //given
        Rocket rocket = null;

        //when
        boolean inRepair = rocketStatusService.changeStatusToInRepair(rocket);

        //then
        assertFalse(inRepair);
    }

    @Test
    void isStatusChangedToOnGround() {
        //given
        Rocket rocket = new Rocket("Dragon 1");

        //when
        boolean inSpace = rocketStatusService.changeStatusToOnGround(rocket);

        //then
        assertTrue(inSpace);
        assertEquals(RocketStatus.ON_GROUND, rocket.getStatus());
    }

    @Test
    void ifRocketIsNullWhenChangeToOnGroundReturnFalse() {
        //given
        Rocket rocket = null;

        //when
        boolean inSpace = rocketStatusService.changeStatusToOnGround(rocket);

        //then
        assertFalse(inSpace);
    }

}