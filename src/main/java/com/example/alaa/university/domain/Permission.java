package com.example.alaa.university.domain;

public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    DEFAULT_READ("default:read"),
    EDITOR_UPDATE("editor:update"),
    EDITOR_DELETE("editor:delete"),
    EDITOR_CREATE("editor:create");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}

