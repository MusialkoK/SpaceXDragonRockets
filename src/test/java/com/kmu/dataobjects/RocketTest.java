package com.kmu.dataobjects;

import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RocketTest {

    @Test
    void isStatusOnGroundSetWhenCreated(){
        //given + when
        Rocket rocket = new Rocket("TestName");

        //then
        assertEquals(RocketStatus.ON_GROUND,rocket.getStatus());
    }

    @Test
    void isNameSet(){
        //given
        String testName = "TestName";

        //when
        Rocket rocket = new Rocket(testName);

        //then
        assertEquals(rocket.getName(), testName);
    }

    @Test
    void rocketSummaryInFormat() {
        //given
        String rocketSummaryFormat = "  ◦ %s - %s\n";

        String rocketName = "Dragon 1";
        Rocket rocket = new Rocket(rocketName);
        String statusName = rocket.getStatus().getStatusName();

        //when
        String summary = rocket.getSummary();

        //then
        assertEquals(String.format(rocketSummaryFormat, rocketName, statusName), summary);
    }
}
