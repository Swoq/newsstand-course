package com.swoqe.newsstand.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    PLAIN_USER(Set.of(Permission.PLAIN_USER_WRITE, Permission.PLAIN_USER_READ)),
    PUBLISHER_USER(Set.of(Permission.PUBLISHERS_WRITE, Permission.PUBLISHERS_READ, Permission.PLAIN_USER_WRITE, Permission.PLAIN_USER_READ)),
    SYS_ADMIN(new HashSet<>(Arrays.asList(Permission.values())));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
