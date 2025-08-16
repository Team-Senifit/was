package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.CooldownWorkoutKind;
import com.senifit.was.entity.selections.IncludesSinging;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_singing_kinds")
@NoArgsConstructor
public class LookupWorkoutSingingKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutSingingKind, IncludesSinging> {

    public static LookupWorkoutSingingKind fromSelection(IncludesSinging selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutSingingKind.class, selection);
    }

    @Override
    public IncludesSinging toSelection(LookupWorkoutSingingKind selection) {
        return IncludesSinging.fromId(selection.getId());
    }
}

