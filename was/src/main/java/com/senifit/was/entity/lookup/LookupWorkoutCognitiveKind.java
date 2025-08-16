package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_cognitive_kinds")
@NoArgsConstructor
public class LookupWorkoutCognitiveKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutCognitiveKind, CognitiveWorkoutKind> {

    public static LookupWorkoutCognitiveKind fromSelection(CognitiveWorkoutKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutCognitiveKind.class, selection);
    }

    @Override
    public CognitiveWorkoutKind toSelection() {
        return CognitiveWorkoutKind.fromId(this.getId());
    }
}

