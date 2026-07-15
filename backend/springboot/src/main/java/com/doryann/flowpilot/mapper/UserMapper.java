package com.doryann.flowpilot.mapper;

import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(AppUser user);
}
