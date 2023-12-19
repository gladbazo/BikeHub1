package com.bikehub.model.mapper;

import com.bikehub.model.dto.UserRegisterDTO;
import com.bikehub.model.entity.User;
import com.bikehub.model.view.UserProfileView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapping {

    @Mapping(target = "active", constant = "true")
    User userDtoToUser(UserRegisterDTO userRegisterDTO);

    UserProfileView userToProfileView(User user);
}