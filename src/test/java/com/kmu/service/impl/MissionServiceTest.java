package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @InjectMocks
    private MissionService missionService;


    @Test
    void assignOnGroundRocketsToMissionSuccessful(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");

        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

        //then
        assertTrue(success);
        assertEquals(rocketSet.size(), mission.getAssignedRockets().size());
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
        rocketSet.forEach(aRocket -> assertionForSingleRocketIfSuccessfullyAssigned(aRocket, mission));
    }

    @Test
    void assignRocketsToNullMissionReturnsFalse(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");

        Mission mission = null;

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

        //then
        assertFalse(success);
        assertEquals(RocketStatus.ON_GROUND, rocket.getStatus());
        assertEquals(RocketStatus.ON_GROUND, rocket2.getStatus());
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
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

        //then
        assertTrue(success);
        assertEquals(rocketSet.size(), mission.getAssignedRockets().size());
        assertEquals(MissionStatus.PENDING, mission.getStatus());
        assertTrue(mission.getAssignedRockets().contains(rocket));
        assertEquals(mission, rocket.getCurrentMission());
        assertTrue(mission.getAssignedRockets().contains(rocket2));
        assertEquals(mission, rocket2.getCurrentMission());
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
        assertEquals(RocketStatus.IN_SPACE, rocket2.getStatus());
    }

    @Test
    void assignInSpaceRocketToMissionReturnsFalse(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        rocket.setStatus(RocketStatus.IN_SPACE);

        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);
        Set<Rocket> assignedRockets = Set.of(rocket2);

        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

        //then
        assertFalse(success);
        assertEquals(assignedRockets.size(), mission.getAssignedRockets().size());
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
        assignedRockets.forEach(aRocket -> assertionForSingleRocketIfSuccessfullyAssigned(aRocket, mission));
        assertNull(rocket.getCurrentMission());
    }

    private void assertionForSingleRocketIfSuccessfullyAssigned(Rocket rocket, Mission mission){
        assertTrue(mission.getAssignedRockets().contains(rocket));
        assertEquals(mission, rocket.getCurrentMission());
        assertEquals(RocketStatus.IN_SPACE, rocket.getStatus());
    }
}