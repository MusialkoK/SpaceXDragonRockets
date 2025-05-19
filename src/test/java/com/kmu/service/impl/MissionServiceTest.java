package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.service.SpaceXService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @BeforeEach
    void setUp() {
        missionService = new MissionService(missionSet);
    }

    private final SpaceXService spaceXService = new SpaceXService();

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


    @Test
    void addRocketToMissionSuccessful(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Mission mission = new Mission("Luna");

        //when
        boolean added = missionService.addRocketToMission(rocket, mission);

        //then

        assertTrue(added);
        assertTrue(mission.getAssignedRockets().contains(rocket));
        assertEquals(1, mission.getAssignedRockets().size());
    }

    @Test
    void addRocketToMissionNullReturnFalse(){
        //given
        Rocket rocket = new Rocket("Dragon 1");
        Mission mission = null;

        //when
        boolean added = missionService.addRocketToMission(rocket, mission);

        //then

        assertFalse(added);
        assertDoesNotThrow(() -> missionService.addRocketToMission(rocket, mission));
    }

    @Test
    void addRocketNullToMissionReturnFalse(){
        //given
        Rocket rocket = null;
        Mission mission = new Mission("Luna");

        //when
        boolean added = missionService.addRocketToMission(rocket, mission);

        //then

        assertFalse(added);
        assertTrue(mission.getAssignedRockets().isEmpty());
        assertDoesNotThrow(() -> missionService.addRocketToMission(rocket, mission));
    }



    @Test
    void summaryForSingleMissionNoRockets(){
        //given
        Mission mission = new Mission("Luna");
        missionService.addNewMission(mission);

        //when
        String summary = missionService.getMissionsSummary();

        //then
        assertEquals("- Luna - Scheduled - Dragons: 0", summary);
    }

    @Test
    void summaryForSingleMissionTwoRockets(){
        //given
        Mission mission = new Mission("Luna");
        missionService.addNewMission(mission);
        Rocket rocket1 = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        spaceXService.assignRocketsToMission(mission, Set.of(rocket1, rocket2));

        String expected = """
                - Luna - In progress - Dragons: 2
                  - Dragon 1 - In space
                  - Dragon 2 - In space""";

        //when
        String summary = missionService.getMissionsSummary();

        //then
        assertEquals(expected, summary);
    }

    @Test
    void summaryForThreeMissionsDifferentNumberOfRocketsShouldBeInRocketCountOrder(){
        //given

        Mission mission1 = new Mission("Luna");
        missionService.addNewMission(mission1);
        Rocket rocket1 = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        spaceXService.assignRocketsToMission(mission1, Set.of(rocket1, rocket2));

        Mission mission2 = new Mission("Venus");
        missionService.addNewMission(mission2);

        Mission mission3 = new Mission("Ceres");
        missionService.addNewMission(mission3);
        Rocket rocket3 = new Rocket("Dragon 3");
        spaceXService.assignRocketsToMission(mission3, Set.of(rocket3));

        String expected = """
                - Luna - In progress - Dragons: 2
                  - Dragon 1 - In space
                  - Dragon 2 - In space
                - Ceres - In progress - Dragons: 1
                  - Dragon 3 - In space
                - Venus - Scheduled - Dragons: 0""";

        //when
        String summary = missionService.getMissionsSummary();

        //then
        assertEquals(expected, summary);
    }

    @Test
    void summaryForThreeMissionsSameNumberOfRocketsShouldBeInDescAlphabeticalOrder(){
        //given

        Mission mission1 = new Mission("Luna");
        missionService.addNewMission(mission1);
        Rocket rocket1 = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        spaceXService.assignRocketsToMission(mission1, Set.of(rocket1, rocket2));

        Mission mission2 = new Mission("Ceres");
        missionService.addNewMission(mission2);
        Rocket rocket3 = new Rocket("Dragon 3");
        Rocket rocket4 = new Rocket("Dragon 4");
        spaceXService.assignRocketsToMission(mission2, Set.of(rocket3, rocket4));

        Mission mission3 = new Mission("Venus");
        missionService.addNewMission(mission3);
        Rocket rocket5 = new Rocket("Dragon 5");
        Rocket rocket6 = new Rocket("Dragon 6");
        spaceXService.assignRocketsToMission(mission3, Set.of(rocket5, rocket6));

        String expected = """
                - Venus - In progress - Dragons: 2
                  - Dragon 5 - In space
                  - Dragon 6 - In space
                - Luna - In progress - Dragons: 2
                  - Dragon 1 - In space
                  - Dragon 2 - In space
                - Ceres - In progress - Dragons: 2
                  - Dragon 3 - In space
                  - Dragon 4 - In space""";

        //when
        String summary = missionService.getMissionsSummary();

        //then
        assertEquals(expected, summary);
    }

    @Test
    void clearAssignedRockets(){
        //given
        Mission mission = new Mission("Luna");
        Rocket rocket = new Rocket("Dragon 1");
        mission.getAssignedRockets().add(rocket);
        ///when
        missionService.clearAssignedRockets(mission);
        //then
        assertTrue(mission.getAssignedRockets().isEmpty());
    }

    @Test
    void clearAssignedRocketsWhenMissionNull(){
        //given + when
        Mission mission = null;
        //then
        assertDoesNotThrow(() -> missionService.clearAssignedRockets(mission));
    }
}