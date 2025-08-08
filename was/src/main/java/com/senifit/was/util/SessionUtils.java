package com.senifit.was.util;

import com.senifit.was.exception.api.common.UnauthenticatedSessionException;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    public static Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new UnauthenticatedSessionException();
        }
        return userId;
    }
}