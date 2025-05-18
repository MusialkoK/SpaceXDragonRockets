package com.kmu.service.impl;

import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import com.kmu.service.RocketStatusServiceInterface;

public class RocketStatusService implements RocketStatusServiceInterface {
    @Override
    public boolean changeStatusToInSpace(Rocket rocket) {
        if(rocket == null) return false;
        rocket.setStatus(RocketStatus.IN_SPACE);
        return true;
    }
}
