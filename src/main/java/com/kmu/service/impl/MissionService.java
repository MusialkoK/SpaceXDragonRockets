package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.dataobject.Rocket;
import com.kmu.dataobject.RocketStatus;
import com.kmu.service.MissionServiceInterface;

import java.util.Set;

public class MissionService implements MissionServiceInterface {
    private static MissionService instance;

    private final MissionStatusService missionStatusService = MissionStatusService.getInstance();

    public static MissionService getInstance(){
        if(instance == null) instance = new MissionService();
        return instance;
    }

    private MissionService() {
    }

    @Override
    public MissionStatus updateMissionStatus(Mission mission) {
        Set<Rocket> assignedRockets = mission.getAssignedRockets();
        if(assignedRockets.isEmpty()){
            missionStatusService.changeStatusToScheduled(mission);
            return MissionStatus.SCHEDULED;
        }
        if(hasAtLeastOneRocketInRepair(assignedRockets)){
            missionStatusService.changeStatusToPending(mission);
            return MissionStatus.PENDING;
        }
        missionStatusService.changeStatusToInProgress(mission);
        return MissionStatus.IN_PROGRESS;
    }

    private boolean hasAtLeastOneRocketInRepair(Set<Rocket> assignedRockets){
        return assignedRockets.stream().anyMatch(rocket -> rocket.getStatus().equals(RocketStatus.IN_REPAIR));
    }
}
