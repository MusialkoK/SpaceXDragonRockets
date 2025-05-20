package com.kmu.service.impl;

import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;

public class RocketStatusService {

    private static RocketStatusService instance;

    private RocketStatusService() {
    }

    public static RocketStatusService getInstance() {
        if (instance == null) instance = new RocketStatusService();
        return instance;
    }

    private boolean changeStatusTo(Rocket rocket, RocketStatus newStatus) {
        if (rocket == null) return false;
        rocket.setStatus(newStatus);
        return true;
    }

    boolean changeStatusToInSpace(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.IN_SPACE);
    }

    boolean changeStatusToInRepair(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.IN_REPAIR);
    }

    boolean changeStatusToOnGround(Rocket rocket) {
        return changeStatusTo(rocket, RocketStatus.ON_GROUND);
    }
}
