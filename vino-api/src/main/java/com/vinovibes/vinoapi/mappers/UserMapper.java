package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.user.RatingUserDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.dtos.user.UserDto;
import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "otp", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

    @Mapping(target = "name", expression = "java(mapFirstAndLastNameToName(user))")
    RatingUserDto toRatingUserDto(User user);

    @Named("statusToString")
    default String mapStatusToString(UserStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("firstAndLastNameToName")
    default String mapFirstAndLastNameToName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
