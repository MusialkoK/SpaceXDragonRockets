package com.kmu.service.impl;

import com.kmu.dataobject.Rocket;
import com.kmu.service.RocketServiceInterface;

import java.util.Map;

public class RocketService implements RocketServiceInterface {

    private final Map<String, Rocket> rocketMap;

    public RocketService(Map<String, Rocket> rocketMap) {
        this.rocketMap = rocketMap;
    }

    @Override
    public boolean addNewRocket(Rocket rocket) {
        Rocket mapObject = rocketMap.put(rocket.getName(), rocket);
        return mapObject == null;
    }
}
