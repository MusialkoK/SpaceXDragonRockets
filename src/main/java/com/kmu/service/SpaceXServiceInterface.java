package com.kmu.service;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

public interface SpaceXServiceInterface {

    boolean changeRocketStatusToInRepair(Rocket rocket);

    boolean changeMissionStatusToEnded(Mission mission);


}
