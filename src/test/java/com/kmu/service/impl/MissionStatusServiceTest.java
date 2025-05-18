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
        boolean isPending = missionStatusService.changeStatusToPending(mission);

        //then
        assertEquals(MissionStatus.PENDING, mission.getStatus());
        assertTrue(isPending);
    }

    @Test
    void ifMissionIsNullReturnFalse() {
        //given
        Mission mission = null;
        //when
        boolean isPending = missionStatusService.changeStatusToPending(mission);

        //then
        assertFalse(isPending);
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

}