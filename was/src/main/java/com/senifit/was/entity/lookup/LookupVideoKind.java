package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseGlobalEnumLookupEntity;
import com.senifit.was.entity.base.BaseLookupEntity;
import com.senifit.was.entity.selections.VideoKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "lookup_content_kinds")
@NoArgsConstructor
public class LookupVideoKind extends BaseLookupEntity implements BaseGlobalEnumLookupEntity<LookupVideoKind, VideoKind> {

    @Column(name = "description", nullable = false, length = 128)
    private String description = "";

    public static LookupVideoKind fromSelection(VideoKind selection) {
        return BaseGlobalEnumLookupEntity.fromSelection(LookupVideoKind.class, selection);
    }

    @Override
    public VideoKind toSelection(LookupVideoKind selection) {
        return VideoKind.fromId(selection.getId());
    }
}

