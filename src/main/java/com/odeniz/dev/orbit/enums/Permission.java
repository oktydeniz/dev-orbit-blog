package com.odeniz.dev.orbit.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN("admin"),
    USER("user");

    @Getter
    private final String permission;
}