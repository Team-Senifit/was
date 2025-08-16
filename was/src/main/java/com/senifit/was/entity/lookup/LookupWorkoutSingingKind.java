package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_singing_kinds")
@NoArgsConstructor
public class LookupWorkoutSingingKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutSingingKind, IncludesSingingWorkout> {

    public static LookupWorkoutSingingKind fromSelection(IncludesSingingWorkout selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutSingingKind.class, selection);
    }

    @Override
    public IncludesSingingWorkout toSelection() {
        return IncludesSingingWorkout.fromId(this.getId());
    }
}

