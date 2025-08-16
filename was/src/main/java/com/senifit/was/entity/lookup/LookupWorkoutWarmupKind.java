package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.WarmupWorkoutKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_warmup_kinds")
@NoArgsConstructor
public class LookupWorkoutWarmupKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutWarmupKind, WarmupWorkoutKind> {

    public static LookupWorkoutWarmupKind fromSelection(WarmupWorkoutKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutWarmupKind.class, selection);
    }

    @Override
    public WarmupWorkoutKind toSelection() {
        return WarmupWorkoutKind.fromId(this.getId());
    }
}

