package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MissionStatusServiceTest {

    @InjectMocks
    private MissionStatusService missionStatusService;

    @Test
    void scheduledIfNoRocketAssigned() {

        //given
        Mission mission = new Mission("Luna");

        //when
        missionStatusService.updateMissionStatus(mission);

        //then
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());
    }

    @Test
    void pendingIfAtLeastOneRocketInRepair() {

        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");

        rocket.setStatus(RocketStatus.IN_REPAIR);

        mission.addRocket(rocket);
        mission.addRocket(rocket2);

        //when
        missionStatusService.updateMissionStatus(mission);

        //then
        assertEquals(MissionStatus.PENDING, mission.getStatus());
    }

    @Test
    void inProgressIfNoRocketsInRepair() {

        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");


        mission.addRocket(rocket);
        mission.addRocket(rocket2);

        //when
        missionStatusService.updateMissionStatus(mission);

        //then
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
    }

    @Test
    void returnNullIfMissionNull(){
        //given
        Mission mission = null;

        //when
        MissionStatus status = missionStatusService.updateMissionStatus(mission);

        //then
        assertNull(status);

    }

    @Test
    void isStatusChangedToPending() {
        //given
        Mission mission = new Mission("Luna");
        //when
        MissionStatus status = missionStatusService.changeStatusToPending(mission);

        //then
        assertEquals(MissionStatus.PENDING, mission.getStatus());
        assertEquals(MissionStatus.PENDING, status);
    }

    @Test
    void ifMissionIsNullOnChangeToPendingReturnNull() {
        //given
        Mission mission = null;
        //when
        MissionStatus status = missionStatusService.changeStatusToPending(mission);

        //then
        assertNull(status);
    }

    @Test
    void isSingleton(){
        //given + when
        MissionStatusService missionStatusService1 = MissionStatusService.getInstance();
        MissionStatusService missionStatusService2 = MissionStatusService.getInstance();

        //then

        assertNotNull(missionStatusService1);
        assertEquals(missionStatusService1, missionStatusService2);
    }

    @Test
    void isStatusChangedToScheduled() {
        //given
        Mission mission = new Mission("Luna");
        missionStatusService.changeStatusToPending(mission);
        //when
        MissionStatus status = missionStatusService.changeStatusToScheduled(mission);

        //then
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());
        assertEquals(MissionStatus.SCHEDULED, status);
    }

    @Test
    void ifMissionIsNullOnChangeToScheduleReturnNull() {
        //given
        Mission mission = null;
        //when
        MissionStatus status = missionStatusService.changeStatusToScheduled(mission);

        //then
        assertNull(status);
    }

    @Test
    void isStatusChangedToInProgress() {
        //given
        Mission mission = new Mission("Luna");
        missionStatusService.changeStatusToPending(mission);
        //when
        MissionStatus status = missionStatusService.changeStatusToInProgress(mission);

        //then
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
        assertEquals(MissionStatus.IN_PROGRESS, status);
    }

    @Test
    void ifMissionIsNullOnChangeToInProgressReturnNull() {
        //given
        Mission mission = null;
        //when
        MissionStatus status = missionStatusService.changeStatusToInProgress(mission);

        //then
        assertNull(status);
    }

    @Test
    void isStatusChangedToEndedWithoutRocketsAssigned() {
        //given
        Mission mission = new Mission("Luna");
        missionStatusService.changeStatusToInProgress(mission);
        //when
        MissionStatus status = missionStatusService.changeStatusToEnded(mission);

        //then
        assertEquals(MissionStatus.ENDED, mission.getStatus());
        assertEquals(MissionStatus.ENDED, status);
    }

    @Test
    void isStatusChangedToEndedWithRocketsAssigned() {
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket1 = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        MissionService missionService = MissionService.getInstance();
        missionService.assignRocketsToMission(mission, Set.of(rocket1, rocket2));
        //when
        MissionStatus status = missionStatusService.changeStatusToEnded(mission);

        //then
        assertEquals(MissionStatus.ENDED, mission.getStatus());
        assertEquals(MissionStatus.ENDED, status);
        assertEquals(0, mission.getAssignedRockets().size());
        assertTrue(mission.getAssignedRockets().stream().allMatch(rocket -> rocket.getStatus().equals(RocketStatus.ON_GROUND)));
        assertNull(rocket1.getCurrentMission());
        assertNull(rocket2.getCurrentMission());
    }

    @Test
    void ifMissionIsNullOnChangeToEndedReturnNull() {
        //given
        Mission mission = null;
        //when
        MissionStatus status = missionStatusService.changeStatusToEnded(mission);

        //then
        assertNull(status);
    }

}