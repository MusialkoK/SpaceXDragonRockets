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
        //when
        MissionStatus status = missionStatusService.updateMissionStatus(null);

        //then
        assertNull(status);
        assertDoesNotThrow(() ->missionStatusService.updateMissionStatus(null));

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
        //when
        boolean status = missionStatusService.changeStatusToPending(null);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToPending(null));

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
        //when
        boolean status = missionStatusService.changeStatusToScheduled(null);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToScheduled(null));
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
        //when
        boolean status = missionStatusService.changeStatusToInProgress(null);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToInProgress(null));

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
        //when
        boolean status = missionStatusService.changeStatusToEnded(null);

        //then
        assertFalse(status);
        assertDoesNotThrow(() ->missionStatusService.changeStatusToEnded(null));

    }
}