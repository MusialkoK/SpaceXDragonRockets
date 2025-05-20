package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

import java.util.Collection;

public interface SpaceXServiceInterface {

    boolean changeRocketStatusToInRepair(Rocket rocket);
    boolean changeMissionStatusToEnded(Mission mission);
    boolean assignRocketToMission(Rocket rocket, Mission mission);
    boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet);
    boolean addNewMission(Mission mission);
    String getMissionsSummary();
    boolean addNewRocket(Rocket rocket);

}
