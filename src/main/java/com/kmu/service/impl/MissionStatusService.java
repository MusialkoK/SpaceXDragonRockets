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
    public boolean changeStatusToPending(Mission mission) {
        if(mission == null) return false;
        mission.setStatus(MissionStatus.PENDING);
        return true;
    }
}
