package ru.sbt;

import java.util.Date;

public class User {
    private final String fio;

    private final Department department;

    private final Date birthday;

    public User(String fio, Department department, Date birthday) {
        this.fio = fio;
        this.department = department;
        this.birthday = birthday;
    }

    public String getFio() {
        return fio;
    }

    public Department getDepartment() {
        return department;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override public String toString() {
        return "User{" +
            "fio='" + fio + '\'' +
            ", department=" + department +
            ", birthday=" + birthday +
            '}';
    }
}
