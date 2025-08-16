package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.BundleKind;
import com.senifit.was.entity.selections.RoutineKind;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lookup_bundle_kinds")
@NoArgsConstructor
public class LookupBundleKind
        extends BaseLookupEntity
        implements BaseGlobalEnumLookupEntity<LookupBundleKind, BundleKind> {

    public static LookupBundleKind fromSelection(BundleKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupBundleKind.class, selection);
    }

    @Override
    public BundleKind toSelection(LookupBundleKind selection) {
        return BundleKind.fromId(selection.getId());
    }
}

