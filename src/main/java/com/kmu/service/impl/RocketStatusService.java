package com.kmu.service.impl;

import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import com.kmu.service.RocketStatusServiceInterface;

public class RocketStatusService implements RocketStatusServiceInterface {

    private static RocketStatusService instance;
    public static RocketStatusService getInstance(){
        if(instance == null) instance = new RocketStatusService();
        return instance;
    }

    private RocketStatusService() {
    }

    @Override
    public boolean changeStatusToInSpace(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.IN_SPACE);
    }

    @Override
    public boolean changeStatusToInRepair(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.IN_REPAIR);
    }

    @Override
    public boolean changeStatusToOnGround(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.ON_GROUND);
    }

    private boolean changeStatusTo(Rocket rocket, RocketStatus newStatus){
        if(rocket == null) return false;
        rocket.setStatus(newStatus);
        return true;
    }
}
