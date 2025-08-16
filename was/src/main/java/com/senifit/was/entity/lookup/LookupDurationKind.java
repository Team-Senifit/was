package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.DurationKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_duration_kinds")
@NoArgsConstructor
public class LookupDurationKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupDurationKind, DurationKind> {

    public static LookupDurationKind fromSelection(DurationKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupDurationKind.class, selection);
    }

    @Override
    public DurationKind toSelection() {
        return DurationKind.fromId(this.getId());
    }
}

