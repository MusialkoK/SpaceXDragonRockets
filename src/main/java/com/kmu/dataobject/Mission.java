package com.kmu.dataobject;

import java.util.Objects;

public class Mission {

    private final String name;
    private final MissionStatus status;

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
