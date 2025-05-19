package com.kmu.service;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;

public interface MissionStatusServiceInterface {

    MissionStatus changeStatusToPending(Mission mission);
    MissionStatus changeStatusToScheduled(Mission mission);
    MissionStatus changeStatusToInProgress(Mission mission);

    MissionStatus changeStatusToInEnded(Mission mission);

    MissionStatus updateMissionStatus(Mission mission);
}
