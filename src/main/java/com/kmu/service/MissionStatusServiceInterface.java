package com.kmu.service;

import com.kmu.dataobject.Mission;

public interface MissionStatusServiceInterface {

    boolean changeStatusToPending(Mission mission);
}
