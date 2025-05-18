package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @InjectMocks
    private MissionService missionService;

    @Test
    void scheduledIfNoRocketAssigned() {

        //given
        Mission mission = new Mission("Luna");

        //when
        missionService.updateMissionStatus(mission);

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
        missionService.updateMissionStatus(mission);

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
        missionService.updateMissionStatus(mission);

        //then
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
    }

    @Test
    void returnNullIfMissionNull(){
        //given
        Mission mission = null;

        //when
        MissionStatus status = missionService.updateMissionStatus(mission);

        //then
        assertNull(status);

    }
}