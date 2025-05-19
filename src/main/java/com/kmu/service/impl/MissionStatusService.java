package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;
import com.kmu.model.Rocket;
import com.kmu.model.RocketStatus;

import java.util.Set;

public class MissionStatusService{

    private static MissionStatusService instance;

    public static MissionStatusService getInstance(){
        if(instance == null) instance = new MissionStatusService();
        return instance;
    }

    private MissionStatusService() {
    }

    boolean changeStatusToPending(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.PENDING);
    }

    boolean changeStatusToScheduled(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.SCHEDULED);
    }


    boolean changeStatusToInProgress(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.IN_PROGRESS);

    }

    boolean changeStatusToEnded(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.ENDED);
    }

    MissionStatus updateMissionStatus(Mission mission) {
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

    private boolean changeMissionStatusTo(Mission mission, MissionStatus status){
        if(mission == null) return false;
        mission.setStatus(status);
        return true;
    }

    private boolean hasAtLeastOneRocketInRepair(Set<Rocket> assignedRockets){
        return assignedRockets.stream().anyMatch(rocket -> rocket.getStatus().equals(RocketStatus.IN_REPAIR));
    }
}
