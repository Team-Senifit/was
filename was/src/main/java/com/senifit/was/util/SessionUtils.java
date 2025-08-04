package com.senifit.was.util;

import com.senifit.was.exception.api.common.UnauthenticatedSessionException;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    public static Long getCenterId(HttpSession session) {
        Long centerId = (Long) session.getAttribute("centerId");
        if (centerId == null) {
            throw new UnauthenticatedSessionException();
        }
        return centerId;
    }
}