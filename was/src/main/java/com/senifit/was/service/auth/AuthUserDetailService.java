package com.senifit.was.service.auth;

import com.senifit.was.dto.request.center.CenterCreateRequest;
import com.senifit.was.entity.Center;
import com.senifit.was.repository.center.CentersRepository;
import com.senifit.was.security.CenterDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final CentersRepository centerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Center center = centerRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("AuthUserDetailService: centerId 없음"));
        return CenterDetail.builder()
                .loginId(center.getLoginId())
                .centerId(center.getCenterId())
                .password(center.getPassword())
                .role(center.getRole())
                .build();
    }
}
