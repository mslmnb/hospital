package com.epam.hospital.model;

import org.json.JSONObject;

import static com.epam.hospital.model.Role.ROLE_ADMIN;
import static com.epam.hospital.service.UserService.*;

public class User extends BaseEntity implements HavingJsonView {

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
        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put(ID_PARAMETER, getId());
        userJsonObj.put(STAFF_ID_PARAMETER, getStaffId());
        userJsonObj.put(STAFF_NAME_PARAMETER, staff.getSurnameWithInitials());
        userJsonObj.put(LOGIN_PARAMETER, login);
        userJsonObj.put(ROLE_PARAMETER, role);
        return userJsonObj.toString();
    }

    @Override
    public String toString() {
        return staff.getSurnameWithInitials();
    }
}
