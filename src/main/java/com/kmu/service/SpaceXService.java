package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.service.impl.MissionService;
import com.kmu.service.impl.MissionStatusService;
import com.kmu.service.impl.RocketService;
import com.kmu.service.impl.RocketStatusService;

public class SpaceXService implements SpaceXServiceInterface {

    private final RocketStatusService rocketStatusService;
    private final MissionStatusService missionStatusService;
    private final RocketService rocketService;
    private final MissionService missionService;

    public SpaceXService(RocketStatusService rocketStatusService, MissionStatusService missionStatusService, RocketService rocketService, MissionService missionService) {
        this.rocketStatusService = rocketStatusService;
        this.missionStatusService = missionStatusService;
        this.rocketService = rocketService;
        this.missionService = missionService;
    }

    @Override
    public boolean changeRocketStatusToInRepair(Rocket rocket) {
        if(rocket == null) return false;
        missionStatusService.changeStatusToPending(rocket.getCurrentMission());
        return rocketStatusService.changeStatusToInRepair(rocket);
    }

    @Override
    public boolean changeMissionStatusToEnded(Mission mission) {
        if(mission == null) return false;
        mission.getAssignedRockets().forEach(this::unAssignRocketToMission);
        missionService.clearAssignedRockets(mission);
        missionStatusService.changeStatusToEnded(mission);
        return true;
    }

    private void unAssignRocketToMission(Rocket rocket){
        rocketStatusService.changeStatusToOnGround(rocket);
        rocketService.clearRocketCurrentMission(rocket);
    }
}
