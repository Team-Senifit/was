package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.CognitiveKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_cognitive_kinds")
@NoArgsConstructor
public class LookupWorkoutCognitiveKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutCognitiveKind, CognitiveKind> {

    public static LookupWorkoutCognitiveKind fromSelection(CognitiveKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutCognitiveKind.class, selection);
    }

    @Override
    public CognitiveKind toSelection() {
        return CognitiveKind.fromId(this.getId());
    }
}

