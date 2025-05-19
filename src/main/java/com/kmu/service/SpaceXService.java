package com.kmu.service;

import com.kmu.model.Rocket;
import com.kmu.service.impl.MissionStatusService;
import com.kmu.service.impl.RocketStatusService;

public class SpaceXService implements SpaceXServiceInterface {

    private final RocketStatusService rocketStatusService;
    private final MissionStatusService missionStatusService;

    public SpaceXService(RocketStatusService rocketStatusService, MissionStatusService missionStatusService) {
        this.rocketStatusService = rocketStatusService;
        this.missionStatusService = missionStatusService;
    }

    @Override
    public boolean changeRocketStatusToInRepair(Rocket rocket) {
        if(rocket == null) return false;
        missionStatusService.changeStatusToPending(rocket.getCurrentMission());
        return rocketStatusService.changeStatusToInRepair(rocket);
    }
}
