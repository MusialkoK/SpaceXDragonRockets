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

import java.util.Set;

import static com.kmu.model.MissionStatus.IN_PROGRESS;
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
        mission.setStatus(IN_PROGRESS);
        rocket.setStatus(RocketStatus.IN_SPACE);


        doNothing().when(missionService).clearAssignedRockets(mission);
        when(missionStatusService.changeStatusToEnded(mission)).thenReturn(true);
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

    @Test
    void assignRocketToMissionSuccessful() {
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        when(rocketService.assignRocketToMission(rocket, mission)).thenReturn(true);
        when(rocketStatusService.changeStatusToInSpace(rocket)).thenReturn(true);
        when(missionService.addRocketToMission(rocket, mission)).thenReturn(true);
        when(missionStatusService.updateMissionStatus(mission)).thenReturn(IN_PROGRESS);
        //when
        boolean isAssigned = spaceXService.assignRocketToMission(rocket, mission);

        //then
        assertTrue(isAssigned);
        verify(rocketService, times(1)).assignRocketToMission(rocket, mission);
        verify(rocketStatusService, times(1)).changeStatusToInSpace(rocket);
        verify(missionService, times(1)).addRocketToMission(rocket, mission);
        verify(missionStatusService, times(1)).updateMissionStatus(mission);
    }

    @Test
    void assignRocketToMissionReturnFalseIfMissionNull() {
        //given
        Mission mission = null;
        Rocket rocket = new Rocket("Dragon 1");

        //when
        boolean isAssigned = spaceXService.assignRocketToMission(rocket, mission);
        //then
        assertFalse(isAssigned);
        verify(rocketService, never()).assignRocketToMission(rocket, mission);
        verify(rocketStatusService, never()).changeStatusToInSpace(rocket);
        verify(missionService, never()).addRocketToMission(rocket, mission);
        verify(missionStatusService, never()).updateMissionStatus(mission);
        assertDoesNotThrow(() -> spaceXService.assignRocketToMission(rocket, mission));

    }

    @Test
    void assignRocketToMissionReturnFalseIfRocketNull() {
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = null;

        //when
        boolean isAssigned = spaceXService.assignRocketToMission(rocket, mission);
        //then
        assertFalse(isAssigned);
        verify(rocketService, never()).assignRocketToMission(rocket, mission);
        verify(rocketStatusService, never()).changeStatusToInSpace(rocket);
        verify(missionService, never()).addRocketToMission(rocket, mission);
        verify(missionStatusService, never()).updateMissionStatus(mission);
        assertDoesNotThrow(() -> spaceXService.assignRocketToMission(rocket, mission));
    }

    @Test
    void notAssignRocketIfAlreadyInSpace() {
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        rocket.setStatus(RocketStatus.IN_SPACE);

        //when
        boolean isAssigned = spaceXService.assignRocketToMission(rocket, mission);

        //then
        assertFalse(isAssigned);
        verify(rocketService, never()).assignRocketToMission(rocket, mission);
        verify(rocketStatusService, never()).changeStatusToInSpace(rocket);
        verify(missionService, never()).addRocketToMission(rocket, mission);
        verify(missionStatusService, never()).updateMissionStatus(mission);
        assertDoesNotThrow(() -> spaceXService.assignRocketToMission(rocket, mission));
    }

    @Test
    void assignOnGroundRocketsToMissionSuccessful(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = spaceXService.assignRocketsToMission(mission, rocketSet);

        //then
        assertTrue(success);
    }

    @Test
    void assignRocketsToNullMissionReturnsFalse(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");

        Mission mission = null;

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = spaceXService.assignRocketsToMission(mission, rocketSet);

        //then
        assertFalse(success);
        assertDoesNotThrow(() -> spaceXService.assignRocketsToMission(mission, rocketSet));


    }

    @Test
    void assignInRepairRocketToMissionSuccessful(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        rocket.setStatus(RocketStatus.IN_REPAIR);

        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = spaceXService.assignRocketsToMission(mission, rocketSet);

        //then
        assertTrue(success);
    }

    @Test
    void assignInSpaceRocketToMissionReturnsFalse(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        rocket.setStatus(RocketStatus.IN_SPACE);

        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = spaceXService.assignRocketsToMission(mission, rocketSet);

        //then
        assertFalse(success);
    }
}