package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

import java.util.Collection;

public interface MissionServiceInterface {

    boolean addNewMission(Mission mission);

    boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet);

    String getMissionsSummary();

}
