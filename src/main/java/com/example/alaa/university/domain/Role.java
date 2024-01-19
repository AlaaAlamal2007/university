package com.example.alaa.university.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.alaa.university.domain.Permission.*;

public enum Role {
    DEFAULT(Set.of(DEFAULT_READ)),
    EDITOR(Set.of(EDITOR_UPDATE, EDITOR_DELETE, EDITOR_CREATE)),
    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_CREATE, ADMIN_DELETE));

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorties = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorties.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorties;
    }
}


