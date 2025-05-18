package com.kmu.service;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.MissionStatus;

public interface MissionServiceInterface {

    MissionStatus updateMissionStatus(Mission mission);
}
