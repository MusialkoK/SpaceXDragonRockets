package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.MissionStatus;

public interface MissionStatusServiceInterface {

    boolean changeStatusToPending(Mission mission);
    boolean changeStatusToScheduled(Mission mission);
    boolean changeStatusToInProgress(Mission mission);
    boolean changeStatusToEnded(Mission mission);
    MissionStatus updateMissionStatus(Mission mission);
}
