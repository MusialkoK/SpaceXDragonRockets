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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceXServiceTest {

    @Mock
    private final RocketStatusService rocketStatusService = RocketStatusService.getInstance();
    @Mock
    private final MissionStatusService missionStatusService = MissionStatusService.getInstance();
    @Mock
    private final RocketService rocketService = RocketService.getInstance();
    @Mock
    private final MissionService missionService = MissionService.getInstance();
    @InjectMocks
    private SpaceXService spaceXService;

    @BeforeEach
    void setUp() {
        this.spaceXService = new SpaceXService(rocketStatusService, missionStatusService, rocketService, missionService);
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
        assertDoesNotThrow(() -> spaceXService.changeRocketStatusToInRepair(rocket));
    }

    @Test
    void changeMissionStatusToEnded(){
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        rocket.setCurrentMission(mission);
        mission.getAssignedRockets().add(rocket);
        mission.setStatus(MissionStatus.IN_PROGRESS);
        rocket.setStatus(RocketStatus.IN_SPACE);


        doNothing().when(missionService).clearAssignedRockets(mission);
        when(missionStatusService.changeStatusToEnded(mission)).thenReturn(null);
        when(rocketStatusService.changeStatusToOnGround(rocket)).thenReturn(true);
        doNothing().when(rocketService).clearRocketCurrentMission(rocket);
        //when
        boolean isStatusChanged = spaceXService.changeMissionStatusToEnded(mission);

        //then
        assertTrue(isStatusChanged);
        verify(missionService, times(1)).clearAssignedRockets(mission);
        verify(missionStatusService, times(1)).changeStatusToEnded(mission);
        verify(rocketStatusService, times(1)).changeStatusToOnGround(rocket);
        verify(rocketService, times(1)).clearRocketCurrentMission(rocket);
    }

    @Test
    void changeMissionStatusToEndedMissionNull() {
        //given
        Mission mission = null;
        //when
        boolean isStatusChanged = spaceXService.changeMissionStatusToEnded(mission);
        //then
        assertFalse(isStatusChanged);
        assertDoesNotThrow(() -> spaceXService.changeMissionStatusToEnded(mission));
    }
}