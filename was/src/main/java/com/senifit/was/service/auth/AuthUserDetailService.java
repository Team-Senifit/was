package com.senifit.was.service.auth;

import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.security.AuthUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final CentersRepository centersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthUserDetail(centersRepository.findByid(username)
                .orElseThrow(() -> new UsernameNotFoundException("AuthUserDetailService: id 없음"))
        );
    }
}
