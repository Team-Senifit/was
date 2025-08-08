package com.senifit.was.security;

import com.senifit.was.entity.Center;
import com.senifit.was.entity.CenterRole;
import com.senifit.was.repository.center.CentersRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.CredentialsContainer;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CenterDetail implements UserDetails, CredentialsContainer {

    private Long id;
    private String centerId;
    private String passwordHash;
    private CenterRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
    @Override
    public String getPassword() {
        return passwordHash;
    }
    @Override
    public String getUsername() {
        return centerId;
    }
    @Override
    public void eraseCredentials() {
        this.passwordHash = null;
    }
}
