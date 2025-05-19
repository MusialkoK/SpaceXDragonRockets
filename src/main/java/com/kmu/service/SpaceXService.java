package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import com.kmu.service.impl.MissionService;
import com.kmu.service.impl.MissionStatusService;
import com.kmu.service.impl.RocketService;
import com.kmu.service.impl.RocketStatusService;

import java.util.Collection;

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

    public SpaceXService() {
        this.rocketStatusService = RocketStatusService.getInstance();
        this.missionStatusService = MissionStatusService.getInstance();
        this.rocketService = RocketService.getInstance();
        this.missionService = MissionService.getInstance();
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

    @Override
    public boolean assignRocketToMission(Rocket rocket, Mission mission) {
        if(rocket == null || mission == null) return false;
        if(rocket.getStatus().equals(RocketStatus.IN_SPACE)) return false;
        rocketService.assignRocketToMission(rocket, mission);
        if(!rocket.getStatus().equals(RocketStatus.IN_REPAIR)) rocketStatusService.changeStatusToInSpace(rocket);
        missionService.addRocketToMission(rocket, mission);
        missionStatusService.updateMissionStatus(mission);
        return true;
    }

    @Override
    public boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet) {
        if(mission == null) return false;
        return rocketSet.stream()
                .map(rocket -> assignRocketToMission(rocket,mission))
                .allMatch(aBoolean -> aBoolean.equals(true));
    }

    private void unAssignRocketToMission(Rocket rocket){
        rocketStatusService.changeStatusToOnGround(rocket);
        rocketService.clearRocketCurrentMission(rocket);
    }
}
