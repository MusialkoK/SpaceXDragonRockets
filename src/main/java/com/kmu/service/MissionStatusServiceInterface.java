package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;

public interface MissionStatusServiceInterface {

    MissionStatus changeStatusToPending(Mission mission);
    MissionStatus changeStatusToScheduled(Mission mission);
    MissionStatus changeStatusToInProgress(Mission mission);

    MissionStatus changeStatusToEnded(Mission mission);

    MissionStatus updateMissionStatus(Mission mission);
}
