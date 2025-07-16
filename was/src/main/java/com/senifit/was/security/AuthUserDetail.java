package com.senifit.was.security;

import com.senifit.was.entity.Centers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthUserDetail implements UserDetails {

    private final Centers center;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(center.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return center.getPassword();
    }

    @Override
    public String getUsername() {
        return center.getId();
    }
}
