package com.senifit.was.util;

import com.github.f4b6a3.tsid.TsidCreator;
import org.springframework.stereotype.Component;

@Component
public class TsidIdGenerator {
    public Long nextId() {
        return TsidCreator.getTsid().toLong();
    }
}
