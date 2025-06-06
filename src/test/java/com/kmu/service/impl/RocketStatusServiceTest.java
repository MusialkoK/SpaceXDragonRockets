package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RocketStatusServiceTest {
    @InjectMocks
    private RocketStatusService rocketStatusService;


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
        //when
        boolean inSpace = rocketStatusService.changeStatusToInSpace(null);

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

        //when
        boolean inRepair = rocketStatusService.changeStatusToInRepair(null);

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

        //when
        boolean inSpace = rocketStatusService.changeStatusToOnGround(null);

        //then
        assertFalse(inSpace);
    }

}