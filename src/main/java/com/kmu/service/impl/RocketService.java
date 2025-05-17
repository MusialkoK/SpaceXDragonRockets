package com.kmu.service.impl;

import com.kmu.dataobject.Rocket;
import com.kmu.service.RocketServiceInterface;

import java.util.HashMap;
import java.util.Map;

public class RocketService implements RocketServiceInterface {

    private static RocketService instance;

    private final Map<String, Rocket> rocketMap;

    private RocketService(Map<String, Rocket> rocketMap) {
        this.rocketMap = rocketMap;
    }

    public static RocketService getInstance(){
        if(instance == null) instance = new RocketService(new HashMap<>());
        return instance;
    }

    @Override
    public boolean addNewRocket(Rocket rocket) {
        if(rocket == null) return false;
        if(validateRocketName(rocket.name())) return false;
        if(rocketMap.containsKey(rocket.name())) return false;
        rocketMap.put(rocket.name(), rocket);
        return true;
    }

    private boolean validateRocketName(String rocketName){
        return rocketName == null || rocketName.isEmpty();
    }
}
