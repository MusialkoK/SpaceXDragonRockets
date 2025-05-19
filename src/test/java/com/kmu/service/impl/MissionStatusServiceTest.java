package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertDoesNotThrow(() ->missionStatusService.updateMissionStatus(mission));

    }

    @Test
    void isStatusChangedToPending() {
        //given
        Mission mission = new Mission("Luna");
        //when
        boolean status = missionStatusService.changeStatusToPending(mission);

        //then
        assertEquals(MissionStatus.PENDING, mission.getStatus());
        assertTrue(status);
    }

    @Test
    void ifMissionIsNullOnChangeToPendingReturnNull() {
        //given
        Mission mission = null;
        //when
        boolean status = missionStatusService.changeStatusToPending(mission);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToPending(mission));

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
        boolean status = missionStatusService.changeStatusToScheduled(mission);

        //then
        assertTrue(status);
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());
    }

    @Test
    void ifMissionIsNullOnChangeToScheduleReturnNull() {
        //given
        Mission mission = null;
        //when
        boolean status = missionStatusService.changeStatusToScheduled(mission);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToScheduled(mission));
    }

    @Test
    void isStatusChangedToInProgress() {
        //given
        Mission mission = new Mission("Luna");
        missionStatusService.changeStatusToPending(mission);
        //when
        boolean status = missionStatusService.changeStatusToInProgress(mission);

        //then
        assertTrue(status);
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
    }

    @Test
    void ifMissionIsNullOnChangeToInProgressReturnNull() {
        //given
        Mission mission = null;
        //when
        boolean status = missionStatusService.changeStatusToInProgress(mission);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToInProgress(mission));

    }

    @Test
    void isStatusChangedToEndedWithoutRocketsAssigned() {
        //given
        Mission mission = new Mission("Luna");
        mission.setStatus(MissionStatus.IN_PROGRESS);
        //when
        boolean status = missionStatusService.changeStatusToEnded(mission);

        //then
        assertTrue(status);
        assertEquals(MissionStatus.ENDED, mission.getStatus());
    }

    @Test
    void ifMissionIsNullOnChangeToEndedReturnNull() {
        //given
        Mission mission = null;
        //when
        boolean status = missionStatusService.changeStatusToEnded(mission);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToEnded(mission));

    }
}