package com.epam.hospital.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.epam.hospital.model.Role.ROLE_ADMIN;

public class User extends BaseEntity implements HavingJsonView{

    private Staff staff;

    private String login;

    private String password;

    private Role role;

    public User() {
    }

    public User(Integer id, Staff staff, String login, String password, Role role) {
        super(id);
        this.staff = staff;
        this.login = login;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role.equals(ROLE_ADMIN);
    }

    @Override
    public String getJsonString() {
        return "{ " +
                "\"id\": " + getId() + ", " +
                "\"staffId\": " + getStaffId() + ", " +
                "\"staffName\": \"" + staff.getSurnameWithInitials() + "\", " +
                "\"login\": \"" + login + "\", " +
                "\"role\": \"" + role + "\" " +
                "}";
    }

    @Override
    public String toString() {
        return staff.getSurnameWithInitials();
    }
}
