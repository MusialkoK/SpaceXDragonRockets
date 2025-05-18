package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.service.MissionStatusServiceInterface;

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
    public MissionStatus changeStatusToInEnded(Mission mission) {
        return changeMissionStatusTo(mission, MissionStatus.ENDED);
    }

    private MissionStatus changeMissionStatusTo(Mission mission, MissionStatus status){
        if(mission == null) return null;
        mission.setStatus(status);
        return status;
    }
}
