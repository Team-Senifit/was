package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.TargetKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Table(name = "lookup_targets")
public class LookupTarget
extends BaseLookupEntity
implements BaseGlobalEnumLookupEntity<LookupTarget, TargetKind> {

    @Column(name = "description", nullable = false, length = 128)
    private String description = "";

    public static LookupTarget fromSelection(TargetKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupTarget.class, selection);
    }

    @Override
    public TargetKind toSelection(LookupTarget selection) {
        return TargetKind.fromCode(selection.getName());
    }
}

