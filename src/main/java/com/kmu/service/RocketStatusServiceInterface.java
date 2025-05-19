package com.kmu.service;

import com.kmu.model.Rocket;

public interface RocketStatusServiceInterface {

    boolean changeStatusToInSpace(Rocket rocket);

    boolean changeStatusToInRepair(Rocket rocket);

    boolean changeStatusToOnGround(Rocket rocket);

}
