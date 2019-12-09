/*package com.yaji.traderev.carauction.util;

import com.yaji.traderev.carauction.annotations.MappedToClass;
import com.yaji.traderev.carauction.annotations.MappedToField;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityOperations {

  private <X> Map<String, String> getFieldsMappedFromModifedDataToOriginal(
      Set<Field> fieldsOfOriginal, X modifiedData) {
    Map<String, String> ret = new HashMap<>();
    Set<Field> fieldsOfModifiedData = getFieldsUpTo(modifiedData.getClass(), null);
    for (Field each : fieldsOfModifiedData) {
      if (fieldsOfOriginal.contains(each.getName())) {
        ret.put(each.getName(), each.getName());
      } else {
        MappedToField mappedToField = each.getAnnotation(MappedToField.class);
        if (!mappedToField.field().isEmpty()) {
          ret.put(each.getName(), mappedToField.field());
        }
      }
    }
    return ret;
  }

  private <X> boolean verifyMapper(Class originalClass, X modifiedData) {
    MappedToClass mappedToClass = modifiedData.getClass().getAnnotation(MappedToClass.class);
    if (mappedToClass == null || mappedToClass.value().isEmpty()) {
      return false;
    }
    return mappedToClass.value().equals(originalClass);
  }

  public <T, X> T mergeNonNullModifiableFields(T original, X modifiedData)
      throws TradeRevException {
    Class originalClass = original.getClass();
    Class modifiedDataClass = modifiedData.getClass();
    if (!verifyMapper(originalClass, modifiedData)) {
      log.error(
          "DTO Class {} is not mapped to Entity Class {}. Please set the correct value in @MappedToClass annotation in {}",
          modifiedDataClass,
          originalClass,
          modifiedDataClass);
      throw new TradeRevException(
          ErrorCode.WRONG_DTO_TO_ENTITY_MAPPING, modifiedDataClass, originalClass);
    }
    Set<Field> fieldsOfOriginal = getFieldsUpTo(originalClass, null);

    Map<String, String> fieldsMappedFromModifedDataToOriginal = getFieldsMappedFromModifedDataToOriginal(fieldsOfOriginal, modifiedData);
    for(Field each: fieldsOfOriginal){
    	if(each.getAnnotation(ModifiableField.class) != null){
    		each.setAccessible(true);
    		Field modifiedField = fieldsMappedFromModifedDataToOriginal.ge
    		try{
    			Object modifiedVal = each.get(withModifiedFields);
    			if(modifiedVal != null){
    				each.set(original, modifiedVal);
    			}
    		} catch(IllegalAccessException e){
    			throw new TradeRevException(e, ErrorCode.OBJECT_COULD_NOT_BE_MODIFIED, original, withModifiedFields);
    		}
    	}
    }
    return original;
  }

  public static Set<Field> getFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent) {

    List<Field> currentClassFields = Arrays.asList(startClass.getDeclaredFields());
    Class<?> parentClass = startClass.getSuperclass();

    if (parentClass != null
        && (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
      List<Field> parentClassFields = (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
      currentClassFields.addAll(parentClassFields);
    }

    Set<Field> ret = new HashSet<Field>();
    ret.addAll(currentClassFields);
    return ret;
  }
}
*/
