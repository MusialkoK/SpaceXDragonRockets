package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;
import com.kmu.service.MissionStatusServiceInterface;

public class MissionStatusService implements MissionStatusServiceInterface {
    @Override
    public boolean changeStatusToPending(Mission mission) {
        if(mission == null) return false;
        mission.setStatus(MissionStatus.PENDING);
        return true;
    }
}
