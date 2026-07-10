package com.doryann.flowpilot.mapper;

import com.doryann.flowpilot.api.model.CreateUserBody;
import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.entity.AppUser;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = DateTimeMapper.class)
public interface UserMapper {
    UserDTO toDto(AppUser user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AppUser toEntity(CreateUserBody createUserBody);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget AppUser user);

}
