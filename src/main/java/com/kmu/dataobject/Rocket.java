package com.kmu.dataobject;

import java.util.Objects;

public class Rocket {

    private final String name;
    private RocketStatus status;

    private Mission currentMission;

    public Rocket(String name) {
        this.name = name;
        this.status = RocketStatus.ON_GROUND;
    }

    public String getName() {
        return name;
    }

    public RocketStatus getStatus() {
        return status;
    }

    public void setStatus(RocketStatus status) {
        this.status = status;
    }

    public Mission getCurrentMission() {
        return currentMission;
    }

    public void setCurrentMission(Mission currentMission) {
        this.currentMission = currentMission;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Rocket rocket)) return false;
        return Objects.equals(getName(), rocket.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
