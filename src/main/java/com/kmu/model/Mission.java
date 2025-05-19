package com.kmu.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Mission {

    private static final String MISSION_SUMMARY_HEADER_FORMAT = "- %s - %s - Dragons: %d";

    private final String name;
    private MissionStatus status;

    private Set<Rocket> assignedRockets = new HashSet<>();

    public Mission(String name) {
        this.name = name;
        this.status = MissionStatus.SCHEDULED;
    }

    public String getName() {
        return name;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Set<Rocket> getAssignedRockets() {
        return assignedRockets;
    }

    public void setAssignedRockets(Set<Rocket> assignedRockets) {
        this.assignedRockets = assignedRockets;
    }

    public void addRocket(Rocket rocket){
        assignedRockets.add(rocket);
    }
    public String getSummary() {
        String header = String.format(MISSION_SUMMARY_HEADER_FORMAT, name, status.getStatusName(), assignedRockets.size());
        if(assignedRockets.isEmpty()) return header;
        return assignedRockets.stream()
                .sorted(alphabetically())
                .map(Rocket::getSummary)
                .collect(Collectors.joining("\n", header + "\n",""));
    }

    private Comparator<Rocket> alphabetically(){
        return Comparator.comparing(Rocket::getName);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Mission mission)) return false;
        return Objects.equals(getName(), mission.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Mission{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }


}
