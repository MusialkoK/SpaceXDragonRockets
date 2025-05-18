package com.kmu.service.impl;

import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
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
    void ifRocketIsNullReturnFalse() {
        //given
        Rocket rocket = null;

        //when
        boolean inSpace = rocketStatusService.changeStatusToInSpace(rocket);

        //then
        assertFalse(inSpace);
    }

    @Test
    void isSingleton(){
        //given + when
        RocketStatusService rocketStatusService1 = RocketStatusService.getInstance();
        RocketStatusService rocketStatusService2 = RocketStatusService.getInstance();

        //then

        assertNotNull(rocketStatusService1);
        assertEquals(rocketStatusService1, rocketStatusService2);
    }

}