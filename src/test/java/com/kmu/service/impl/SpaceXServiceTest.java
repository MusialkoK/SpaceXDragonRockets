package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import com.kmu.service.SpaceXService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceXServiceTest {

    @Mock
    private final RocketStatusService rocketStatusService = RocketStatusService.getInstance();
    @Mock
    private final MissionStatusService missionStatusService = MissionStatusService.getInstance();
    @InjectMocks
    private SpaceXService spaceXService;

    @BeforeEach
    void setUp() {
        this.spaceXService = new SpaceXService(rocketStatusService, missionStatusService);
    }

    @Test
    void whenChangingRocketStatusOnRepairChangeMissionStatusToPending() {
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Mission mission = new Mission("Luna");
        rocket.setCurrentMission(mission);
        when(missionStatusService.changeStatusToPending(mission)).thenCallRealMethod();
        when(rocketStatusService.changeStatusToInRepair(rocket)).thenCallRealMethod();

        //when
        boolean inRepair = spaceXService.changeRocketStatusToInRepair(rocket);

        //then
        assertTrue(inRepair);
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
        assertEquals(MissionStatus.PENDING, mission.getStatus());
    }

    @Test
    void whenChangingRocketStatusButRocketNullReturnFalse() {
        //given
        Rocket rocket = null;

        //when
        boolean inRepair = spaceXService.changeRocketStatusToInRepair(rocket);

        //then
        assertFalse(inRepair);
    }

}