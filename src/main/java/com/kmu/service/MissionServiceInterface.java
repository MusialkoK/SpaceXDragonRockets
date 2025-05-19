package com.kmu.service;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;

import java.util.Collection;

public interface MissionServiceInterface {



    boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet);
}
