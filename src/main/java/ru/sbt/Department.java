package ru.sbt;

import java.io.Serializable;

public class Department {
    private final int id;

    private final String name;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override public String toString() {
        return "Department{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
