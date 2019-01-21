package com.epam.hospital.to;

import com.epam.hospital.model.User;

public class AuthorizedUser {
    private int id;
    private String name;

    public AuthorizedUser(User user) {
        this.id = user.getStaff().getId();
        this.name = user.getStaff().getSurnameWithInitials();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
