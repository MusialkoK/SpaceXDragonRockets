package com.kmu.dataobject;

import java.util.Objects;

public class Rocket {

    private String name;

    public Rocket(String name){
        this.name = name;
    }

    public String getName() {
        return name;
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
                '}';
    }
}
