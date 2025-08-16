package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.WorkoutPurposeKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "lookup_workout_purposes")
@Getter
public class LookupWorkoutPurpose
extends BaseLookupEntity
implements BaseGlobalEnumLookupEntity<LookupWorkoutPurpose, WorkoutPurposeKind> {

    @Column(name = "description", nullable = false, length = 128)
    private String description = "";

    public static LookupWorkoutPurpose fromSelection(WorkoutPurposeKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupWorkoutPurpose.class, selection);
    }

    @Override
    public WorkoutPurposeKind toSelection() {
        return WorkoutPurposeKind.fromCode(this.getName());
    }
}

