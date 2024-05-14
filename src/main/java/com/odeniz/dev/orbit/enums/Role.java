package com.odeniz.dev.orbit.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(Permission.ADMIN)),
    USER(Set.of(Permission.USER));

    @Getter
    private final Set<Permission> permissionSet;

    public List<SimpleGrantedAuthority> authorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = permissionSet.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
