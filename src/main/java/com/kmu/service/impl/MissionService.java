package com.kmu.service.impl;

import com.kmu.dataobject.Mission;
import com.kmu.dataobject.Rocket;
import com.kmu.service.MissionServiceInterface;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MissionService implements MissionServiceInterface {
    private static MissionService instance;
    private final Set<Mission> missionSet;
    private final RocketService rocketService;
    private MissionService() {
        this.missionSet = new HashSet<>();
        this.rocketService = RocketService.getInstance();
    }

    MissionService(RocketService rocketService, Set<Mission> missionSet){
        this.rocketService = rocketService;
        this.missionSet = missionSet;
    }

    public static MissionService getInstance(){
        if(instance == null) instance = new MissionService();
        return instance;
    }

    @Override
    public boolean addNewMission(Mission mission) {
        if(mission == null) return false;
        if(validateMissionName(mission.getName())) return false;
        if(missionSet.contains(mission)) return false;
        missionSet.add(mission);
        return true;
    }

    @Override
    public boolean assignRocketsToMission(Mission mission, Collection<Rocket> rocketSet) {
        if(mission == null) return false;
        return rocketSet.stream()
                .map(rocket -> rocketService.assignRocketToMission(rocket,mission))
                .allMatch(aBoolean -> aBoolean.equals(true));
    }

    private boolean validateMissionName(String missionName){
        return missionName == null || missionName.isEmpty();
    }
}
