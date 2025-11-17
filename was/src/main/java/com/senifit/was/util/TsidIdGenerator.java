package com.senifit.was.util;

import com.github.f4b6a3.tsid.TsidCreator;
import org.springframework.stereotype.Component;

@Component
public class TsidIdGenerator {

    // 2^53 - 1 (JavaScript Number.MAX_SAFE_INTEGER)
    // 프론트엔드에서 64bit 범위까지 고려하지 않은 것으로 확인되어
    // 기본 타입인 Number의 최대 값을 사용합니다.
    // 물론 충돌 가능성은 극히 낮아 고려할 필요는 없으나 기존 TSID 방식보다 늘어난건 사실.
    //
    // TODO: 이 문제 해결 필요.
    private static final long MAX_SAFE_INTEGER = 9007199254740991L; 

    public Long nextId() {
        long tsidValue = TsidCreator.getTsid().toLong();
        return Math.abs(tsidValue % MAX_SAFE_INTEGER);
    }
}
