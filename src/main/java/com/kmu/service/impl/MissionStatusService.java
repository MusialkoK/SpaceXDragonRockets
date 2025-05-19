package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;
import com.kmu.service.MissionStatusServiceInterface;

import java.util.Set;

public class MissionStatusService implements MissionStatusServiceInterface {

    private static MissionStatusService instance;

    public static MissionStatusService getInstance(){
        if(instance == null) instance = new MissionStatusService();
        return instance;
    }

    private MissionStatusService() {
    }

    @Override
    public MissionStatus changeStatusToPending(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.PENDING);
    }

    @Override
    public MissionStatus changeStatusToScheduled(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.SCHEDULED);
    }

    @Override
    public MissionStatus changeStatusToInProgress(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.IN_PROGRESS);

    }

    @Override
    public MissionStatus changeStatusToEnded(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.ENDED);
    }

    @Override
    public MissionStatus updateMissionStatus(Mission mission) {
        if(mission == null) return null;
        Set<Rocket> assignedRockets = mission.getAssignedRockets();
        if(assignedRockets.isEmpty()){
            changeStatusToScheduled(mission);
            return MissionStatus.SCHEDULED;
        }
        if(hasAtLeastOneRocketInRepair(assignedRockets)){
            changeStatusToPending(mission);
            return MissionStatus.PENDING;
        }
        changeStatusToInProgress(mission);
        return MissionStatus.IN_PROGRESS;
    }

    private MissionStatus changeMissionStatusTo(Mission mission, MissionStatus status){
        if(mission == null) return null;
        mission.setStatus(status);
        return status;
    }

    private boolean hasAtLeastOneRocketInRepair(Set<Rocket> assignedRockets){
        return assignedRockets.stream().anyMatch(rocket -> rocket.getStatus().equals(RocketStatus.IN_REPAIR));
    }
}
