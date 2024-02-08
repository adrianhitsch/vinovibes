package com.vinovibes.vinoapi.mappers;

import com.vinovibes.vinoapi.dtos.user.RatingUserDto;
import com.vinovibes.vinoapi.dtos.user.SignUpDto;
import com.vinovibes.vinoapi.dtos.user.UserDto;
import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for users.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Maps the user to a userDTO. Target token is ignored. Status is mapped to a string.
     * @param user user
     * @return userDTO
     */
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    UserDto toUserDto(User user);

    /**
     * Maps the signUpDTO to a user entity. Target id, status, token and otp are ignored.
     * @param signUpDto signUpDTO
     * @return user
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "otp", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

    /**
     * Maps the user to a ratingUserDTO. Maps the first and last name to a name.
     * @param user user
     * @return ratingUserDTO
     */
    @Mapping(target = "name", expression = "java(mapFirstAndLastNameToName(user))")
    RatingUserDto toRatingUserDto(User user);

    /**
     * Maps the userStatus enum to a string.
     * @param status status
     * @return string
     */
    @Named("statusToString")
    default String mapStatusToString(UserStatus status) {
        return status != null ? status.name() : null;
    }

    /**
     * Maps the first and last name to a name.
     * @param user user
     * @return string
     */
    @Named("firstAndLastNameToName")
    default String mapFirstAndLastNameToName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
