package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.service.RocketServiceInterface;

import java.util.HashSet;
import java.util.Set;

public class RocketService implements RocketServiceInterface {

    private static RocketService instance;

    private final Set<Rocket> rocketSet;

    private RocketService() {
        this.rocketSet = new HashSet<>();
    }

    RocketService(Set<Rocket> missionSet){
        this.rocketSet = missionSet;
    }
    public static RocketService getInstance(){
        if(instance == null) instance = new RocketService();
        return instance;
    }

    @Override
    public boolean addNewRocket(Rocket rocket) {
        if(rocket == null) return false;
        if(validateRocketName(rocket.getName())) return false;
        if(rocketSet.contains(rocket)) return false;
        rocketSet.add(rocket);
        return true;
    }

    @Override
    public boolean assignRocketToMission(Rocket rocket, Mission mission) {
        if(rocket == null || mission == null) return false;
        rocket.setCurrentMission(mission);
        return true;
    }

    public void clearRocketCurrentMission(Rocket rocket) {
        if(rocket == null) return;
        rocket.setCurrentMission(null);

    }

    private boolean validateRocketName(String rocketName){
        return rocketName == null || rocketName.isEmpty();
    }
}
