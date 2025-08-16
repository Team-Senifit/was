package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.CooldownWorkoutKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_workout_cooldown_kinds")
@NoArgsConstructor
public class LookupWorkoutCooldownKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupWorkoutCooldownKind, CooldownWorkoutKind> {

    public static LookupWorkoutCooldownKind fromSelection(CooldownWorkoutKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutCooldownKind.class, selection);
    }

    @Override
    public CooldownWorkoutKind toSelection() {
        return CooldownWorkoutKind.fromId(this.getId());
    }
}

