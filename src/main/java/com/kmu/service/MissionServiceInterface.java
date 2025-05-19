package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

public interface MissionServiceInterface {

    boolean addNewMission(Mission mission);

    String getMissionsSummary();

    void clearAssignedRockets(Mission mission);

    boolean addRocketToMission(Rocket rocket, Mission mission);

}
