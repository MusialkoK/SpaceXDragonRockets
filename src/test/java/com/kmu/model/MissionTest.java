package com.kmu.model;

import com.kmu.service.impl.SpaceXService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {

    private static final  String MISSION_SUMMARY_HEADER_FORMAT = "- %s - %s - Dragons: %d";

    @Test
    void isNewMissionAdded() {
        //given + when
        String name = "Luna";
        Mission mission = new Mission(name);

        //then
        assertNotNull(mission);
        assertEquals(name, mission.getName());
    }

    @Test
    void isMissionStatusScheduledOnCreation() {
        //given + when
        Mission mission = new Mission("Luna");

        //then
        assertNotNull(mission);
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());
    }

    @Test
    void missionSummaryInFormatNoRockets() {
        //given

        String missionName = "Luna";
        Mission mission = new Mission(missionName);
        String statusName = mission.getStatus().getStatusName();
        int rocketsAssignCount = mission.getAssignedRockets().size();

        String summaryHeader = String.format(MISSION_SUMMARY_HEADER_FORMAT, mission.getName(), statusName, rocketsAssignCount);

        //when
        String summary = mission.getSummary();

        //then
        assertEquals(summary, summaryHeader);
    }

    @Test
    void missionSummaryInFormatWithRockets() {
        //given

        String missionName = "Luna";
        Mission mission = new Mission(missionName);
        Rocket rocket1 = new Rocket("Dragon 1");
        Rocket rocket2 = new Rocket("Dragon 2");
        SpaceXService spaceXService = new SpaceXService();
        spaceXService.assignRocketsToMission(mission, Set.of(rocket1, rocket2));
        String statusName = mission.getStatus().getStatusName();
        int rocketsAssignCount = mission.getAssignedRockets().size();

        String summaryHeader = String.format(MISSION_SUMMARY_HEADER_FORMAT, mission.getName(), statusName, rocketsAssignCount);

        //when
        String summary = mission.getSummary();
        System.out.println(summary + "|");
        System.out.println(summaryHeader + "|");
        //then

        assertTrue(summary.startsWith(summaryHeader + "\n"));
        assertEquals(3L, summary.lines().count());
    }
}
