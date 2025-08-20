package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.DurationKind;
import com.senifit.was.entity.selections.SpecializedWorkoutKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_specialized_workout_kinds")
@NoArgsConstructor
public class LookupSpecializedWorkoutKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupSpecializedWorkoutKind, SpecializedWorkoutKind> {

    public static LookupSpecializedWorkoutKind fromSelection(SpecializedWorkoutKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupSpecializedWorkoutKind.class, selection);
    }

    @Override
    public SpecializedWorkoutKind toSelection() {
        return SpecializedWorkoutKind.fromId(this.getId());
    }
}

