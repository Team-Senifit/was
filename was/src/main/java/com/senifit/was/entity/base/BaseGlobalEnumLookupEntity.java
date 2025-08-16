package com.senifit.was.entity.base;

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

   T toSelection();
}