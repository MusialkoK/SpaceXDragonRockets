package com.kmu.service.impl;

import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import com.kmu.service.RocketStatusServiceInterface;

public class RocketStatusService implements RocketStatusServiceInterface {

    private static RocketStatusService instance;
    private final MissionStatusService missionStatusService;

    public static RocketStatusService getInstance(){
        if(instance == null) instance = new RocketStatusService();
        return instance;
    }

    private RocketStatusService() {
        this.missionStatusService = MissionStatusService.getInstance();
    }

    RocketStatusService(MissionStatusService missionStatusService){
        this.missionStatusService = missionStatusService;
    }

    @Override
    public boolean changeStatusToInSpace(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.IN_SPACE);
    }

    @Override
    public boolean changeStatusToInRepair(Rocket rocket) {
        boolean result = changeStatusTo(rocket, RocketStatus.IN_REPAIR);
        if(!result) return false;
        missionStatusService.changeStatusToPending(rocket.getCurrentMission());
        return true;
    }

    private boolean changeStatusTo(Rocket rocket, RocketStatus newStatus){
        if(rocket == null) return false;
        rocket.setStatus(newStatus);
        return true;
    }
}
