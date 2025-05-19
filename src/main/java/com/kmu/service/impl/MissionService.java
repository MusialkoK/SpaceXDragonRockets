package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;
import com.kmu.service.MissionServiceInterface;

import java.util.Collection;

public class MissionService implements MissionServiceInterface {
    private static MissionService instance;

    private final RocketService rocketService = RocketService.getInstance();

    public static MissionService getInstance(){
        if(instance == null) instance = new MissionService();
        return instance;
    }

    private MissionService() {
    }

    @Override
    public boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet) {
        if(mission == null) return false;
        return rocketSet.stream()
                .map(rocket -> rocketService.assignRocketToMission(rocket,mission))
                .allMatch(aBoolean -> aBoolean.equals(true));
    }
}
