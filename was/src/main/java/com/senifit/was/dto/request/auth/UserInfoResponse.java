package com.senifit.was.dto.request.auth;

import com.senifit.was.entity.Center;

import java.util.List;

public class UserInfoResponse {
    private String username;
    private String nickname;
    private List<Center> centers;
}
