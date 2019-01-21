package com.epam.hospital.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Staff staff;

    private String login;

    private String password;

    private boolean enabled = true;

    private LocalDate registered = LocalDate.now();

    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Staff staff, String login, String password, boolean enabled, LocalDate registered) {
        this.staff = staff;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
