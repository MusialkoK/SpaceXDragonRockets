package com.kmu.dataobject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Mission {

    private final String name;
    private MissionStatus status;

    private final Set<Rocket> assignedRockets = new HashSet<>();

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

    public void addRocket(Rocket rocket){
        assignedRockets.add(rocket);
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
