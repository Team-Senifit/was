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

    private String loginId;
    private Long centerId;
    private String centerName;
    private String password;
    private CenterRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return loginId;
    }
    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
