package com.kmu.service.impl;

import com.kmu.model.Mission;
import com.kmu.model.Rocket;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MissionService {
    private static MissionService instance;
    private final Set<Mission> missionSet;
    private MissionService() {
        this.missionSet = new HashSet<>();
    }

    MissionService(Set<Mission> missionSet){
        this.missionSet = missionSet;
    }

    public static MissionService getInstance(){
        if(instance == null) instance = new MissionService();
        return instance;
    }

    boolean addNewMission(Mission mission) {
        if(mission == null) return false;
        if(validateMissionName(mission.getName())) return false;
        if(missionSet.contains(mission)) return false;
        missionSet.add(mission);
        return true;
    }

    String getMissionsSummary() {
        return missionSet.stream()
                .sorted(byRocketCount().reversed().thenComparing(alphabetically().reversed()))
                .map(Mission::getSummary)
                .collect(Collectors.joining("\n"));
    }

    void clearAssignedRockets(Mission mission) {
        if(mission == null) return;
        mission.getAssignedRockets().clear();
    }

    boolean addRocketToMission(Rocket rocket, Mission mission) {
        if(rocket == null || mission == null) return false;
        mission.addRocket(rocket);
        return true;
    }

    private Comparator<Mission> byRocketCount(){
        return Comparator.comparingInt(mission -> mission.getAssignedRockets().size());
    }

    private Comparator<Mission> alphabetically(){
        return Comparator.comparing(Mission::getName);
    }

    private boolean validateMissionName(String missionName){
        return missionName == null || missionName.isEmpty();
    }
}
