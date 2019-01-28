package com.epam.hospital.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Staff staff;

    private String login;

    private String password;

    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Staff staff, String login, String password) {
        this.staff = staff;
        this.login = login;
        this.password = password;
    }

    public int getStaffId() {
        return staff.getId();
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return staff.getSurnameWithInitials();
    }
}
