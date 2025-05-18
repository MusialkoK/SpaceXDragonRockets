package com.kmu.dataobjects;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MissionTest {

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
}
