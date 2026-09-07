package com.edu.ifce.pecaai.entities;

public enum UserRole {
    ADMIN("ADMIN"),
    GARCOM("GARCOM"),
    CLIENTE("CLIENTE");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}