package com.senifit.was.entity.base;

import com.senifit.was.entity.GlobalEnum;
import com.senifit.was.entity.base.BaseGlobalEnumSelection;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BaseGlobalEnumLookupEntity<
        E extends BaseGlobalEnumLookupEntity<?, ?>,
        T extends BaseGlobalEnumSelection> {

   static <E extends BaseLookupEntity, T extends BaseGlobalEnumSelection>
   E fromSelection(Class<E> entityType, T selection) {
      try {
         E entity = entityType.getDeclaredConstructor().newInstance();
         entity.setId(selection.getId());
         entity.setName(selection.getCode());
         return entity;
      } catch (Exception e) {
         return null;
      }
   }

   T toSelection(E selection);
}