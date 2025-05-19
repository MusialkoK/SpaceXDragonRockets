package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    private MissionService missionService;

    @Spy
    private Set<Mission> missionSet = new HashSet<>();

    @Mock
    private RocketService rocketService;

    @BeforeEach
    void setUp() {
        missionService = new MissionService(rocketService, missionSet);
    }


    @Test
    void assignOnGroundRocketsToMissionSuccessful(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        Mission mission = new Mission("Luna");

        Set<Rocket> rocketSet = Set.of(rocket, rocket2);

        when(rocketService.assignRocketToMission(any(), any())).thenReturn(true).thenReturn(true);
        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

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
        when(rocketService.assignRocketToMission(any(), any())).thenReturn(true).thenReturn(true);

        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

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
        when(rocketService.assignRocketToMission(any(), any())).thenReturn(false).thenReturn(true);

        //when
        boolean success = missionService.assignRocketsToMission(mission, rocketSet);

        //then
        assertFalse(success);
    }

    @Test
    void addMissionWithNotNullUniqueNameToSet() {
        //given
        String missionName = "Luna";
        Mission mission = new Mission(missionName);

        //when
        boolean isAdded = missionService.addNewMission(mission);
        //then
        assertTrue(isAdded);
        verify(missionSet, times(1)).add(mission);
        assertTrue(missionSet.contains(mission));
        assertEquals(1, missionSet.size());
    }

    @Test
    void doNotAddMissionWithNullName() {
        //given
        String missionName = null;
        Mission mission = new Mission(missionName);


        //when
        boolean isAdded = missionService.addNewMission(mission);
        //then
        assertFalse(isAdded);
        verify(missionSet, never()).add(any());
    }

    @Test
    void doNotAddMissionWithEmptyName() {
        //given
        String missionName = "";
        Mission mission = new Mission(missionName);


        //when
        boolean isAdded = missionService.addNewMission(mission);
        //then
        assertFalse(isAdded);
        verify(missionSet, never()).add(any());
    }

    @Test
    void doNotAddRocketIfNameNotUnique() {
        //given
        String missionName = "Luna";
        Mission firstMission = new Mission(missionName);
        Mission secondMission = new Mission(missionName);

        missionService.addNewMission(firstMission);

        //when
        boolean isAdded = missionService.addNewMission(secondMission);
        //then
        assertFalse(isAdded);
        verify(missionSet, times(1)).add(firstMission);
        assertEquals(1,missionSet.size());

    }

    @Test
    void doNotAddIfRocketNull() {
        //given
        Mission mission = null;

        //when
        boolean isAdded = missionService.addNewMission(mission);

        //then
        assertFalse(isAdded);
        verify(missionSet, never()).add(any());
    }

    @Test
    void isSingleton(){
        //given + when
        MissionService missionService1 = MissionService.getInstance();
        MissionService missionService2 = MissionService.getInstance();

        //then

        assertNotNull(missionService1);
        assertEquals(missionService1, missionService2);
    }


}