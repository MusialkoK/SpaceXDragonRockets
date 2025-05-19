package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import com.kmu.service.RocketServiceInterface;

import java.util.HashMap;
import java.util.Map;

public class RocketService implements RocketServiceInterface {

    private static RocketService instance;

    private final Map<String, Rocket> rocketMap;

    private final RocketStatusService rocketStatusService = RocketStatusService.getInstance();
    private final MissionStatusService missionStatusService = MissionStatusService.getInstance();

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
        if(validateRocketName(rocket.getName())) return false;
        if(rocketMap.containsKey(rocket.getName())) return false;
        rocketMap.put(rocket.getName(), rocket);
        return true;
    }

    @Override
    public boolean assignRocketToMission(Rocket rocket, Mission mission) {
        if(rocket == null || mission == null) return false;
        if(rocket.getStatus().equals(RocketStatus.IN_SPACE)) return false;
        rocket.setCurrentMission(mission);
        changeRocketStatus(rocket);
        mission.addRocket(rocket);
        missionStatusService.updateMissionStatus(mission);
        return true;
    }

    private boolean validateRocketName(String rocketName){
        return rocketName == null || rocketName.isEmpty();
    }

    private void changeRocketStatus(Rocket rocket){
        if(rocket.getStatus().equals(RocketStatus.IN_REPAIR)) return;
        rocketStatusService.changeStatusToInSpace(rocket);
    }
}
