package com.senifit.was.entity;

import com.senifit.was.entity.base.BaseTimeEntity;
import com.senifit.was.entity.lookup.LookupBundleKind;
import com.senifit.was.entity.lookup.LookupVideoKind;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "bundles")
@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Bundle extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kind_id", nullable = false)
    private LookupBundleKind kind;

    @Column(name = "duration", nullable = false)
    private Integer duration;
}

