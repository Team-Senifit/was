package com.senifit.was.dto.response.healthcheck;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HealthCheckResponse {
   private final Boolean alive;
   private final Boolean authenticated;
}
