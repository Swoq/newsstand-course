package com.swoqe.newsstand.security;

public enum Permission {
    PUBLISHERS_READ("publisher:read"),
    PUBLISHERS_WRITE("publisher:write"),
    PLAIN_USER_READ("user:read"),
    PLAIN_USER_WRITE("user:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
