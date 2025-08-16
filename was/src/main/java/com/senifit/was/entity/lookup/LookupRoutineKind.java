package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.RoutineKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_routine_kinds")
@NoArgsConstructor
public class LookupRoutineKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupRoutineKind, RoutineKind> {

    public static LookupRoutineKind fromSelection(RoutineKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupRoutineKind.class, selection);
    }

    @Override
    public RoutineKind toSelection() {
        return RoutineKind.fromId(this.getId());
    }
}

