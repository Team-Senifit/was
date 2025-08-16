package com.senifit.was.entity.lookup;

import com.senifit.was.entity.base.BaseLookupEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lookup_genders")
public class LookupGender extends BaseLookupEntity { }

