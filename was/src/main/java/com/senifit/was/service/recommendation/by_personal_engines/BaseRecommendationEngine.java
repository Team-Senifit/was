package com.senifit.was.service.recommendation.by_personal_engines;

import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.vo.ProgramData;

public abstract class BaseRecommendationEngine {
    public abstract ProgramData generate(ByPersonalRequest request);
}
