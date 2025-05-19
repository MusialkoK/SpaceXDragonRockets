package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import com.kmu.service.RocketServiceInterface;

import java.util.HashSet;
import java.util.Set;

public class RocketService implements RocketServiceInterface {

    private static RocketService instance;

    private final Set<Rocket> rocketSet;

    private final RocketStatusService rocketStatusService;
    private final MissionStatusService missionStatusService;

    private RocketService() {
        this.rocketSet = new HashSet<>();
        this.rocketStatusService = RocketStatusService.getInstance();
        this.missionStatusService = MissionStatusService.getInstance();
    }

    RocketService(RocketStatusService rocketStatusService, MissionStatusService missionStatusService, Set<Rocket> missionSet){
        this.rocketStatusService = rocketStatusService;
        this.rocketSet = missionSet;
        this.missionStatusService = missionStatusService;
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
