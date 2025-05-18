package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
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
    void isStatusChangedToEnded() {
        //given
        Mission mission = new Mission("Luna");
        missionStatusService.changeStatusToPending(mission);
        //when
        MissionStatus status = missionStatusService.changeStatusToInEnded(mission);

        //then
        assertEquals(MissionStatus.ENDED, mission.getStatus());
        assertEquals(MissionStatus.ENDED, status);
    }

    @Test
    void ifMissionIsNullOnChangeToEndedReturnNull() {
        //given
        Mission mission = null;
        //when
        MissionStatus status = missionStatusService.changeStatusToInEnded(mission);

        //then
        assertNull(status);
    }

}