package com.senifit.was.util;

import com.senifit.was.exception.api.common.UnauthenticatedSessionException;
import com.senifit.was.security.CenterDetail;
import com.senifit.was.service.auth.AuthUserDetailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUtils {
    public static Long getUserId(HttpSession session) {
        Long centerId = (Long) session.getAttribute("centerId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CenterDetail)) {
            return null;
        }
        CenterDetail cd = (CenterDetail) authentication.getPrincipal();
        return cd.getCenterId();
    }
}