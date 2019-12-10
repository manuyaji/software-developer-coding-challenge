package com.yaji.traderev.carauction.enums.converters;

import com.yaji.traderev.carauction.enums.Role;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Role role) {
    return role.getRoleId();
  }

  @Override
  public Role convertToEntityAttribute(Integer roleId) {
    return Role.getRoleFromRoleId(roleId);
  }
}
