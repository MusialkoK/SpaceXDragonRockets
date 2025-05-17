package com.kmu.dataobject;

import java.util.Objects;

public class Rocket {

    private final String name;
    private final RocketStatus status;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Rocket rocket)) return false;
        return Objects.equals(getName(), rocket.getName()) && getStatus() == rocket.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStatus());
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
