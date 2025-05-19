package com.kmu.service;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;

public interface RocketServiceInterface {

    boolean addNewRocket(Rocket rocket);

    boolean assignRocketToMission(Rocket rocket, Mission mission);
}
