package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

public interface RocketServiceInterface {

    boolean addNewRocket(Rocket rocket);

    boolean assignRocketToMission(Rocket rocket, Mission mission);

    void clearRocketCurrentMission(Rocket rocket);

}
